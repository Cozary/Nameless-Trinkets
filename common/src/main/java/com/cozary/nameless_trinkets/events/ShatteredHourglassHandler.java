package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.ShatteredHourglassBase;
import com.cozary.nameless_trinkets.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ShatteredHourglassHandler {

    public enum TemporalState {
        NONE(0, null),
        TIME_STOP(1, "message.nameless_trinkets.shattered_hourglass_stop"),
        ACCELERATION(2, "message.nameless_trinkets.shattered_hourglass_accelerate"),
        DILATION(3, "message.nameless_trinkets.shattered_hourglass_dilate");

        private final int modeId;
        private final String startMessageKey;

        TemporalState(int modeId, String startMessageKey) {
            this.modeId = modeId;
            this.startMessageKey = startMessageKey;
        }

        public int getModeId() {
            return modeId;
        }

        public String getStartMessageKey() {
            return startMessageKey;
        }
    }

    public static class HourglassSession {
        public final UUID playerUUID;
        public TemporalState currentState = TemporalState.NONE;
        public TemporalState pendingState = TemporalState.NONE;
        public int precastTicks = 0;
        public int pendingDurationTicks = 0;
        public int remainingEffectTicks = 0;
        public int collapseTicks = 0;
        public int cooldownTicks = 0;
        public boolean isEquipped = false;
        public long lastEquippedTickTime = 0;

        public HourglassSession(UUID playerUUID, int initialCooldown) {
            this.playerUUID = playerUUID;
            this.cooldownTicks = initialCooldown;
        }

        public void reset(int newCooldown) {
            this.currentState = TemporalState.NONE;
            this.pendingState = TemporalState.NONE;
            this.precastTicks = 0;
            this.collapseTicks = 0;
            this.cooldownTicks = newCooldown;
        }
    }

    private static final Map<UUID, HourglassSession> SESSIONS = new ConcurrentHashMap<>();
    private static final RandomSource RANDOM = RandomSource.create();
    private static final int PRECAST_TICKS = 50;
    private static volatile UUID activeGlobalDominantPlayer = null;

    public static void tick(LivingEntity entity, ItemStack stack) {
        if (!(entity instanceof ServerPlayer player) || player.isSpectator()) {
            return;
        }

        ShatteredHourglassBase.Stats config = ShatteredHourglassBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            cleanSession(player.getUUID(), player.level().getServer());
            return;
        }

        HourglassSession session = SESSIONS.computeIfAbsent(player.getUUID(),
                id -> new HourglassSession(id, getRandomRangeTicks(config.minCooldownSeconds, config.maxCooldownSeconds)));

        session.isEquipped = true;
        session.lastEquippedTickTime = System.currentTimeMillis();

        if (session.precastTicks > 0) {
            handlePrecastTick(player, session, config);
        } else if (session.collapseTicks > 0) {
            handleCollapseTick(player, session, config);
        } else if (session.currentState == TemporalState.NONE) {
            if (session.cooldownTicks > 0) {
                session.cooldownTicks--;
            } else {
                rollRandomTemporalState(player, session, config);
            }
        } else {
            handleActiveStateTick(player, session, config);
        }
    }

    private static void rollRandomTemporalState(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        if (activeGlobalDominantPlayer != null && !activeGlobalDominantPlayer.equals(player.getUUID())) {
            session.cooldownTicks = 1200; // Delay 1 min
            return;
        }

        TemporalState selectedState = switch (RANDOM.nextInt(3)) {
            case 0 -> TemporalState.TIME_STOP;
            case 1 -> TemporalState.ACCELERATION;
            default -> TemporalState.DILATION;
        };

        int durationTicks = getRandomRangeTicks(config.minEffectDurationSeconds, config.maxEffectDurationSeconds);
        startPrecast(player, session, selectedState, durationTicks);
    }

    private static void startPrecast(ServerPlayer player, HourglassSession session, TemporalState state, int durationTicks) {
        session.pendingState = state;
        session.pendingDurationTicks = durationTicks;
        session.precastTicks = PRECAST_TICKS;
        session.collapseTicks = 0;
        activeGlobalDominantPlayer = player.getUUID();

        resetServerTickRate(player.level().getServer());
        Services.PLATFORM.sendTemporalEffectToPlayer(player, state.getModeId(), false);

        if (player.level() instanceof ServerLevel level) {
            level.playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.2f, 1.2f);
            level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }

    private static void handlePrecastTick(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        if (player.level() instanceof ServerLevel level) {
            if (session.precastTicks == 35 || session.precastTicks == 20 || session.precastTicks == 5) {
                float pitch = 1.0f + (50 - session.precastTicks) / 50.0f;
                level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.8f, pitch);
            }
            if (session.precastTicks == 25 && session.pendingState == TemporalState.TIME_STOP) {
                level.playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.9f, 0.6f);
                level.playSound(null, player.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1.0f, 0.6f);
            }
        }

        session.precastTicks--;
        if (session.precastTicks <= 0) {
            engageActiveState(player, session, config);
        }
    }

    private static void engageActiveState(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        MinecraftServer server = player.level().getServer();
        if (server == null || !(player.level() instanceof ServerLevel level)) {
            return;
        }

        ServerTickRateManager trm = server.tickRateManager();
        TemporalState state = session.pendingState;
        session.currentState = state;
        session.remainingEffectTicks = session.pendingDurationTicks;
        session.pendingState = TemporalState.NONE;
        activeGlobalDominantPlayer = player.getUUID();

        switch (state) {
            case TIME_STOP -> {
                trm.setFrozen(true);
                trm.setTickRate(20.0F);
                level.playSound(null, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.2f, 0.5f);
            }
            case ACCELERATION -> {
                trm.setFrozen(false);
                trm.setTickRate(config.acceleratedTickRate);
                level.playSound(null, player.blockPosition(), SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 1.0f, 1.8f);
            }
            case DILATION -> {
                trm.setFrozen(false);
                trm.setTickRate(config.dilatedTickRate);
                level.playSound(null, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.2f, 0.5f);
                level.playSound(null, player.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1.0f, 0.6f);
            }
            default -> resetServerTickRate(server);
        }

        if (state.getStartMessageKey() != null) {
            player.sendSystemMessage(Component.translatable(state.getStartMessageKey()), true);
        }
    }

    private static void handleActiveStateTick(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        session.remainingEffectTicks--;
        if (session.remainingEffectTicks <= 0) {
            startCollapsing(player, session, config);
        }
    }

    private static void startCollapsing(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        float effectiveTps = switch (session.currentState) {
            case ACCELERATION -> config.acceleratedTickRate;
            case DILATION -> config.dilatedTickRate;
            default -> 20.0F;
        };

        session.collapseTicks = Math.max(1, (int) (1.6F * effectiveTps));
        if (player != null) {
            Services.PLATFORM.sendTemporalEffectToPlayer(player, session.currentState.getModeId(), true);
        }
    }

    private static void handleCollapseTick(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        session.collapseTicks--;
        if (session.collapseTicks <= 0) {
            finishTemporalState(player, session, config);
        }
    }

    private static void finishTemporalState(ServerPlayer player, HourglassSession session, ShatteredHourglassBase.Stats config) {
        MinecraftServer server = player.level().getServer();
        if (server != null && (activeGlobalDominantPlayer == null || activeGlobalDominantPlayer.equals(player.getUUID()))) {
            activeGlobalDominantPlayer = null;
            resetServerTickRate(server);
        }

        if (player.level() instanceof ServerLevel level) {
            playResumeEffects(level, player, session.currentState);
        }

        if (player != null) {
            Services.PLATFORM.sendTemporalEffectToPlayer(player, 0, false);
        }

        session.reset(getRandomRangeTicks(config.minCooldownSeconds, config.maxCooldownSeconds));
    }

    private static void playResumeEffects(ServerLevel level, ServerPlayer player, TemporalState state) {
        switch (state) {
            case TIME_STOP -> {
                level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.2f, 1.6f);
                level.playSound(null, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.8f, 1.8f);
                level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.9f, 1.9f);
            }
            case ACCELERATION -> level.playSound(null, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0f, 1.8f);
            case DILATION -> level.playSound(null, player.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0f, 1.4f);
            default -> {}
        }
        player.sendSystemMessage(Component.translatable("message.nameless_trinkets.shattered_hourglass_resume"), true);
    }

    private static void resetServerTickRate(MinecraftServer server) {
        if (server != null) {
            ServerTickRateManager trm = server.tickRateManager();
            trm.setFrozen(false);
            trm.setTickRate(20.0F);
        }
    }

    private static int getRandomRangeTicks(double minSec, double maxSec) {
        int minTicks = (int) (Math.max(1.0, minSec) * 20);
        int maxTicks = (int) (Math.max(minTicks / 20.0, maxSec) * 20);
        return minTicks + (maxTicks > minTicks ? RANDOM.nextInt(maxTicks - minTicks + 1) : 0);
    }

    public static void cleanSession(UUID playerUUID, MinecraftServer server) {
        SESSIONS.remove(playerUUID);
        if (server == null) return;

        if (playerUUID.equals(activeGlobalDominantPlayer)) {
            activeGlobalDominantPlayer = null;
            resetServerTickRate(server);
        }

        ServerPlayer player = server.getPlayerList().getPlayer(playerUUID);
        if (player != null) {
            Services.PLATFORM.sendTemporalEffectToPlayer(player, 0, false);
        }
    }

}
