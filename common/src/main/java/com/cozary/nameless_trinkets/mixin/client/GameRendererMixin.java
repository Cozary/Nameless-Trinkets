package com.cozary.nameless_trinkets.mixin.client;

import com.cozary.nameless_trinkets.client.ClientTemporalShaderHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        ClientTemporalShaderHelper.updateFrame();
    }

    @Inject(method = "checkEntityPostEffect", at = @At("HEAD"), cancellable = true)
    private void onCheckEntityPostEffect(Entity cameraEntity, CallbackInfo ci) {
        if (ClientTemporalShaderHelper.isActive()) {
            ci.cancel();
        }
    }
}
