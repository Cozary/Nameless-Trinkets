package com.cozary.nameless_trinkets.client;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.mixin.client.GameRendererAccessor;
import com.cozary.nameless_trinkets.mixin.client.PostChainAccessor;
import com.cozary.nameless_trinkets.mixin.client.PostPassAccessor;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientTemporalShaderHelper {

    private static final Identifier SHADER_DESATURATE = Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "desaturate");
    private static final Identifier SHADER_VIBRANT = Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "vibrant");
    private static final Identifier SHADER_PALE = Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "pale");
    private static final Set<Identifier> MAIN_TARGETS = Set.of(PostChain.MAIN_TARGET_ID);

    public static final long EXPANSION_DURATION_MS = 2500;
    public static final long COLLAPSE_DURATION_MS = 1600;
    private static final double MAX_WORLD_RADIUS = 64.0;

    private static int currentMode = 0;
    private static long activationStartTime = 0;
    private static boolean isReversing = false;
    private static long reverseStartTime = 0;

    private static final ByteBuffer UBO_BUFFER = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
    private static GpuBuffer dynamicSquareBuffer = null;

    private record ModeVisuals(float r, float g, float b, int strokeBase, int fillBase, int innerStrokeBase) {}

    private static final ModeVisuals VISUALS_TIME_STOP = new ModeVisuals(
            1.0f, 0.9f, 0.35f,
            ARGB.color(255, 255, 230, 90),
            ARGB.color(255, 255, 217, 76),
            ARGB.color(255, 255, 242, 153)
    );
    private static final ModeVisuals VISUALS_ACCELERATION = new ModeVisuals(
            0.2f, 0.95f, 1.0f,
            ARGB.color(255, 51, 242, 255),
            ARGB.color(255, 25, 230, 255),
            ARGB.color(255, 102, 255, 255)
    );
    private static final ModeVisuals VISUALS_DILATION = new ModeVisuals(
            1.0f, 0.35f, 0.45f,
            ARGB.color(255, 255, 90, 115),
            ARGB.color(255, 242, 64, 102),
            ARGB.color(255, 255, 140, 178)
    );

    public static boolean isActive() {
        return currentMode != 0;
    }

    private static Identifier getShaderForMode(int mode) {
        return switch (mode) {
            case 1 -> SHADER_DESATURATE;
            case 2 -> SHADER_VIBRANT;
            case 3 -> SHADER_PALE;
            default -> null;
        };
    }

    private static ModeVisuals getVisualsForMode(int mode) {
        return switch (mode) {
            case 1 -> VISUALS_TIME_STOP;
            case 2 -> VISUALS_ACCELERATION;
            case 3 -> VISUALS_DILATION;
            default -> VISUALS_TIME_STOP;
        };
    }

    private static float smoothstep(float t) {
        t = Math.min(1.0f, Math.max(0.0f, t));
        return t * t * (3.0f - 2.0f * t);
    }

    public static void setEffect(int mode) {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc == null) return;

            isReversing = false;
            currentMode = mode;
            activationStartTime = System.currentTimeMillis();
            Identifier targetShader = getShaderForMode(mode);

            mc.execute(() -> {
                try {
                    if (mc.gameRenderer != null) {
                        GameRendererAccessor accessor = (GameRendererAccessor) mc.gameRenderer;
                        if (targetShader != null) {
                            accessor.nameless_trinkets$setPostEffectId(targetShader);
                            accessor.nameless_trinkets$setEffectActive(true);
                        } else {
                            mc.gameRenderer.clearPostEffect();
                        }
                    }
                } catch (Throwable t) {
                    NamelessTrinkets.LOG.error("Failed to set temporal post effect: ", t);
                }
            });
        } catch (Throwable t) {
            NamelessTrinkets.LOG.error("Failed to dispatch temporal post effect: ", t);
        }
    }

    public static void triggerReverseEnding() {
        if (currentMode == 0) return;
        isReversing = true;
        reverseStartTime = System.currentTimeMillis();
    }

    public static void updateFrame() {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.level != null) {
            var trm = mc.level.tickRateManager();
            if (trm != null) {
                if (trm.isFrozen()) {
                    if (currentMode != 1) {
                        setEffect(1);
                    }
                } else if (trm.tickrate() > 25.0f) {
                    if (currentMode != 2) {
                        setEffect(2);
                    }
                } else if (trm.tickrate() < 15.0f) {
                    if (currentMode != 3) {
                        setEffect(3);
                    }
                } else {
                    if (currentMode != 0 && !isReversing && (System.currentTimeMillis() - activationStartTime > EXPANSION_DURATION_MS + 2000)) {
                        triggerReverseEnding();
                    }
                }
            }
        }

        if (currentMode == 0) return;

        try {
            if (mc.gameRenderer == null || mc.getShaderManager() == null) return;

            Identifier targetShader = getShaderForMode(currentMode);
            if (targetShader == null) return;

            GameRendererAccessor accessor = (GameRendererAccessor) mc.gameRenderer;
            if (accessor.nameless_trinkets$getPostEffectId() == null || !accessor.nameless_trinkets$isEffectActive()) {
                accessor.nameless_trinkets$setPostEffectId(targetShader);
                accessor.nameless_trinkets$setEffectActive(true);
            }

            PostChain postChain = mc.getShaderManager().getPostChain(targetShader, MAIN_TARGETS);
            if (postChain == null) return;

            List<PostPass> passes = ((PostChainAccessor) postChain).nameless_trinkets$getPasses();
            if (passes == null || passes.isEmpty()) return;

            PostPass firstPass = passes.get(0);
            Map<String, GpuBuffer> uniforms = ((PostPassAccessor) firstPass).nameless_trinkets$getCustomUniforms();
            if (uniforms == null) return;

            boolean isFirstPerson = mc.options.getCameraType().isFirstPerson();
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().position();
            Vec3 center = mc.player != null ? mc.player.position() : cameraPos;
            double distToCenter = cameraPos.distanceTo(center);

            float progress;
            float borderWidth;

            if (isReversing) {
                float elapsed = (System.currentTimeMillis() - reverseStartTime) / (float) COLLAPSE_DURATION_MS;
                if (elapsed >= 1.0f) {
                    currentMode = 0;
                    isReversing = false;
                    mc.gameRenderer.clearPostEffect();
                    return;
                }

                float rawProgress = Math.min(1.0f, Math.max(0.0f, elapsed));
                float reverseScale = 1.0f - smoothstep(rawProgress);
                double currentRadius = reverseScale * MAX_WORLD_RADIUS;
                float borderFade = rawProgress;

                if (isFirstPerson || distToCenter > MAX_WORLD_RADIUS) {
                    progress = reverseScale;
                    borderWidth = 0.012f * borderFade;
                } else {
                    progress = (distToCenter <= currentRadius) ? 1.0f : 0.0f;
                    borderWidth = 0.0f;
                }
            } else {
                float elapsed = (System.currentTimeMillis() - activationStartTime) / (float) EXPANSION_DURATION_MS;
                float rawProgress = Math.min(1.0f, Math.max(0.0f, elapsed));
                float expandScale = smoothstep(rawProgress);
                double currentRadius = expandScale * MAX_WORLD_RADIUS;
                float borderFade = 1.0f - rawProgress;

                if (isFirstPerson || distToCenter > MAX_WORLD_RADIUS) {
                    progress = expandScale;
                    borderWidth = (rawProgress >= 1.0f) ? 0.0f : (0.012f * borderFade);
                } else {
                    progress = (rawProgress >= 1.0f || distToCenter <= currentRadius) ? 1.0f : 0.0f;
                    borderWidth = 0.0f;
                }
            }

            ModeVisuals visuals = getVisualsForMode(currentMode);

            synchronized (UBO_BUFFER) {
                UBO_BUFFER.clear();
                UBO_BUFFER.putFloat(progress);
                UBO_BUFFER.putFloat(borderWidth);
                UBO_BUFFER.putFloat(0.0f);
                UBO_BUFFER.putFloat(0.0f);
                UBO_BUFFER.putFloat(visuals.r);
                UBO_BUFFER.putFloat(visuals.g);
                UBO_BUFFER.putFloat(visuals.b);
                UBO_BUFFER.putFloat(0.0f);
                UBO_BUFFER.flip();

                if (dynamicSquareBuffer == null || dynamicSquareBuffer.isClosed()) {
                    dynamicSquareBuffer = RenderSystem.getDevice().createBuffer(
                            () -> "Nameless Trinkets SquareConfig",
                            GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST,
                            UBO_BUFFER
                    );
                } else {
                    RenderSystem.getDevice().createCommandEncoder().writeToBuffer(dynamicSquareBuffer.slice(), UBO_BUFFER);
                }

                uniforms.put("SquareConfig", dynamicSquareBuffer);
            }
        } catch (Throwable t) {
            NamelessTrinkets.LOG.error("Failed to update temporal shader frame: ", t);
        }
    }

    public static void render3DWorldWave(Camera camera, float deltaPartialTick) {
        if (currentMode == 0) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null || mc.options.getCameraType().isFirstPerson()) return;

        double radius;
        float borderFade;

        if (isReversing) {
            float elapsed = (System.currentTimeMillis() - reverseStartTime) / (float) COLLAPSE_DURATION_MS;
            if (elapsed < 0.0f || elapsed >= 1.0f) return;

            float rawProgress = Math.min(1.0f, Math.max(0.0f, elapsed));
            radius = Math.max(0.2, (1.0f - smoothstep(rawProgress)) * MAX_WORLD_RADIUS);
            borderFade = rawProgress;
        } else {
            float elapsed = (System.currentTimeMillis() - activationStartTime) / (float) EXPANSION_DURATION_MS;
            if (elapsed < 0.0f || elapsed >= 1.0f) return;

            float rawProgress = Math.min(1.0f, Math.max(0.0f, elapsed));
            radius = Math.max(0.5, smoothstep(rawProgress) * MAX_WORLD_RADIUS);
            borderFade = 1.0f - rawProgress;
        }

        if (borderFade <= 0.01f) return;

        Vec3 center = mc.player.getPosition(deltaPartialTick).add(0.0, mc.player.getBbHeight() / 2.0, 0.0);
        AABB outerBox = new AABB(
                center.x - radius, center.y - radius, center.z - radius,
                center.x + radius, center.y + radius, center.z + radius
        );
        AABB innerBox = outerBox.inflate(-0.2 * Math.min(1.0, radius));

        ModeVisuals visuals = getVisualsForMode(currentMode);
        int strokeColor = ARGB.color(Math.round(0.95f * borderFade * 255), ARGB.red(visuals.strokeBase), ARGB.green(visuals.strokeBase), ARGB.blue(visuals.strokeBase));
        int fillColor = ARGB.color(Math.round(0.18f * borderFade * 255), ARGB.red(visuals.fillBase), ARGB.green(visuals.fillBase), ARGB.blue(visuals.fillBase));
        int innerStrokeColor = ARGB.color(Math.round(0.60f * borderFade * 255), ARGB.red(visuals.innerStrokeBase), ARGB.green(visuals.innerStrokeBase), ARGB.blue(visuals.innerStrokeBase));

        try {
            Gizmos.cuboid(outerBox, GizmoStyle.strokeAndFill(strokeColor, 3.5f * borderFade, fillColor));
            if (radius > 1.5) {
                Gizmos.cuboid(innerBox, GizmoStyle.stroke(innerStrokeColor, 1.5f * borderFade));
            }
        } catch (Throwable ignored) {
        }
    }
}
