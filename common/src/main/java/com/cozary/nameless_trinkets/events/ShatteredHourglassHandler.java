package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.AwakenedHourglassBase;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
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

        public boolean isPermanent = false;
        public float activeAcceleratedTickRate = 50.0f;
        public float activeDilatedTickRate = 5.0f;
        public int pendingNextCooldownTicks = 900;

        public long lastTickedServerTick = -1;
        public final Set<Item> equippedInCurrentTick = new LinkedHashSet<>();
        public final List<Item> lastKnownEquipped = new ArrayList<>();
        public int cycleIndex = 0;

        public HourglassSession(UUID playerUUID, int initialCooldown) {
            this.playerUUID = playerUUID;
            this.cooldownTicks = initialCooldown;
            this.pendingNextCooldownTicks = initialCooldown;
        }

        public void reset(int newCooldown) {
            this.currentState = TemporalState.NONE;
            this.pendingState = TemporalState.NONE;
            this.isPermanent = false;
            this.precastTicks = 0;
            this.collapseTicks = 0;
            this.cooldownTicks = newCooldown;
            this.pendingNextCooldownTicks = newCooldown;
        }
    }

    private static final Map<UUID, HourglassSession> SESSIONS = new ConcurrentHashMap<>();
    private static final RandomSource RANDOM = RandomSource.create();
    private static final int PRECAST_TICKS = 50;
    private static volatile UUID activeGlobalDominantPlayer = null;

    public static boolean isAnyTemporalEffectActive() {
        if (activeGlobalDominantPlayer != null) {
            return true;
        }
        for (HourglassSession session : SESSIONS.values()) {
            if (session.currentState != TemporalState.NONE || session.precastTicks > 0 || session.collapseTicks > 0) {
                return true;
            }
        }
        return false;
    }

    private static void checkDominantPlayerSafety(MinecraftServer server) {
        if (activeGlobalDominantPlayer == null || server == null) {
            return;
        }
        ServerPlayer dominant = server.getPlayerList().getPlayer(activeGlobalDominantPlayer);
        if (dominant == null || dominant.isDeadOrDying() || dominant.isSpectator()) {
            cleanSession(activeGlobalDominantPlayer, server);
            return;
        }
        HourglassSession session = SESSIONS.get(activeGlobalDominantPlayer);
        if (session == null || (server.getTickCount() - session.lastTickedServerTick > 20)) {
            cleanSession(activeGlobalDominantPlayer, server);
        }
    }

    public static void tick(LivingEntity entity, ItemStack stack) {
        if (!(entity instanceof ServerPlayer player) || player.isSpectator() || player.isDeadOrDying()) {
            return;
        }

        Item item = stack.getItem();
        if (!(item instanceof ShatteredHourglassBase) && !(item instanceof AwakenedHourglassBase)) {
            return;
        }

        if (item instanceof ShatteredHourglassBase shattered) {
            if (!shattered.getTrinketConfig().isEnable) return;
        } else if (item instanceof AwakenedHourglassBase awakened) {
            if (!awakened.getTrinketConfig().isEnable) return;
        }

        MinecraftServer server = player.level().getServer();
        checkDominantPlayerSafety(server);

        long currentServerTick = server != null ? server.getTickCount() : player.tickCount;

        HourglassSession session = SESSIONS.computeIfAbsent(player.getUUID(), playerUUID -> {
            int initialCooldown;
            if (item instanceof AwakenedHourglassBase awakened) {
                initialCooldown = awakened.getTrinketConfig().isPermanent ? 0 : (int) (Math.max(1.0, awakened.getTrinketConfig().cooldownSeconds) * 20);
            } else if (item instanceof ShatteredHourglassBase shattered) {
                initialCooldown = getRandomRangeTicks(shattered.getTrinketConfig().minCooldownSeconds, shattered.getTrinketConfig().maxCooldownSeconds);
            } else {
                initialCooldown = 900;
            }
            return new HourglassSession(playerUUID, initialCooldown);
        });

        if (session.lastTickedServerTick != currentServerTick) {
            session.lastTickedServerTick = currentServerTick;

            if (!session.equippedInCurrentTick.isEmpty()) {
                session.lastKnownEquipped.clear();
                session.lastKnownEquipped.addAll(session.equippedInCurrentTick);
            }
            session.equippedInCurrentTick.clear();

            progressSessionTick(player, session);
        }

        session.equippedInCurrentTick.add(item);
    }

    private static void progressSessionTick(ServerPlayer player, HourglassSession session) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        checkDominantPlayerSafety(server);

        if (activeGlobalDominantPlayer != null && !activeGlobalDominantPlayer.equals(player.getUUID())) {
            return;
        }

        List<Item> candidates = !session.lastKnownEquipped.isEmpty()
                ? session.lastKnownEquipped
                : (!session.equippedInCurrentTick.isEmpty() ? new ArrayList<>(session.equippedInCurrentTick) : Collections.emptyList());

        AwakenedHourglassBase permanentAwakened = null;
        for (Item item : candidates) {
            if (item instanceof AwakenedHourglassBase awakened && awakened.getTrinketConfig().isEnable && awakened.getTrinketConfig().isPermanent) {
                permanentAwakened = awakened;
                break;
            }
        }

        if (permanentAwakened != null) {
            TemporalState desiredState = permanentAwakened.getTemporalState();
            if (session.currentState == desiredState && session.isPermanent) {
                maintainPermanentState(player, session);
            } else if (session.precastTicks > 0 && session.pendingState == desiredState && session.isPermanent) {
                handlePrecastTick(player, session);
            } else {
                AwakenedHourglassBase.Stats config = permanentAwakened.getTrinketConfig();
                startPrecast(player, session, desiredState, Integer.MAX_VALUE, 0, config.acceleratedTickRate, config.dilatedTickRate, true);
            }
            return;
        }

        if (session.isPermanent) {
            finishTemporalState(player, session);
            return;
        }

        if (session.precastTicks > 0) {
            handlePrecastTick(player, session);
        } else if (session.collapseTicks > 0) {
            handleCollapseTick(player, session);
        } else if (session.currentState == TemporalState.NONE) {
            if (session.cooldownTicks > 0) {
                session.cooldownTicks--;
            } else {
                tryTriggerNextEffect(player, session, candidates);
            }
        } else {
            handleActiveStateTick(player, session);
        }
    }

    private static void applyServerTickRate(ServerTickRateManager tickRateManager, TemporalState state, float acceleratedRate, float dilatedRate) {
        switch (state) {
            case TIME_STOP -> {
                if (!tickRateManager.isFrozen()) {
                    tickRateManager.setFrozen(true);
                }
                if (tickRateManager.tickrate() != 20.0F) {
                    tickRateManager.setTickRate(20.0F);
                }
            }
            case ACCELERATION -> {
                if (tickRateManager.isFrozen()) {
                    tickRateManager.setFrozen(false);
                }
                if (tickRateManager.tickrate() != acceleratedRate) {
                    tickRateManager.setTickRate(acceleratedRate);
                }
            }
            case DILATION -> {
                if (tickRateManager.isFrozen()) {
                    tickRateManager.setFrozen(false);
                }
                if (tickRateManager.tickrate() != dilatedRate) {
                    tickRateManager.setTickRate(dilatedRate);
                }
            }
            default -> {
                tickRateManager.setFrozen(false);
                tickRateManager.setTickRate(20.0F);
            }
        }
    }

    private static void maintainPermanentState(ServerPlayer player, HourglassSession session) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;
        applyServerTickRate(server.tickRateManager(), session.currentState, session.activeAcceleratedTickRate, session.activeDilatedTickRate);
    }

    private static void tryTriggerNextEffect(ServerPlayer player, HourglassSession session, List<Item> candidates) {
        MinecraftServer server = player.level().getServer();

        if (isAnyTemporalEffectActive() || (server != null && server.tickRateManager().isFrozen())) {
            session.cooldownTicks = 20;
            return;
        }

        if (candidates.isEmpty()) {
            return;
        }

        int index = Math.abs(session.cycleIndex) % candidates.size();
        Item chosenItem = candidates.get(index);
        session.cycleIndex++;

        TemporalState state;
        int durationTicks;
        int nextCooldownTicks;
        float acceleratedRate;
        float dilatedRate;

        if (chosenItem instanceof AwakenedHourglassBase awakened) {
            AwakenedHourglassBase.Stats config = awakened.getTrinketConfig();
            state = awakened.getTemporalState();
            durationTicks = (int) (Math.max(1.0, config.effectDurationSeconds) * 20);
            nextCooldownTicks = (int) (Math.max(1.0, config.cooldownSeconds) * 20);
            acceleratedRate = config.acceleratedTickRate;
            dilatedRate = config.dilatedTickRate;
        } else if (chosenItem instanceof ShatteredHourglassBase shattered) {
            ShatteredHourglassBase.Stats config = shattered.getTrinketConfig();
            state = switch (RANDOM.nextInt(3)) {
                case 0 -> TemporalState.TIME_STOP;
                case 1 -> TemporalState.ACCELERATION;
                default -> TemporalState.DILATION;
            };
            durationTicks = getRandomRangeTicks(config.minEffectDurationSeconds, config.maxEffectDurationSeconds);
            nextCooldownTicks = getRandomRangeTicks(config.minCooldownSeconds, config.maxCooldownSeconds);
            acceleratedRate = config.acceleratedTickRate;
            dilatedRate = config.dilatedTickRate;
        } else {
            return;
        }

        startPrecast(player, session, state, durationTicks, nextCooldownTicks, acceleratedRate, dilatedRate, false);
    }

    private static void startPrecast(ServerPlayer player, HourglassSession session, TemporalState state,
                                     int durationTicks, int nextCooldownTicks, float acceleratedRate, float dilatedRate, boolean isPermanent) {
        session.pendingState = state;
        session.currentState = TemporalState.NONE;
        session.pendingDurationTicks = durationTicks;
        session.pendingNextCooldownTicks = nextCooldownTicks;
        session.activeAcceleratedTickRate = acceleratedRate;
        session.activeDilatedTickRate = dilatedRate;
        session.precastTicks = PRECAST_TICKS;
        session.collapseTicks = 0;
        session.isPermanent = isPermanent;
        activeGlobalDominantPlayer = player.getUUID();

        resetServerTickRate(player.level().getServer());
        Services.PLATFORM.sendTemporalEffectToPlayer(player, state.getModeId(), false);

        if (player.level() instanceof ServerLevel level) {
            level.playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.2f, 1.2f);
            level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }

    private static void handlePrecastTick(ServerPlayer player, HourglassSession session) {
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
            engageActiveState(player, session);
        }
    }

    private static void engageActiveState(ServerPlayer player, HourglassSession session) {
        MinecraftServer server = player.level().getServer();
        if (server == null || !(player.level() instanceof ServerLevel level)) {
            return;
        }

        TemporalState state = session.pendingState;
        session.currentState = state;
        session.remainingEffectTicks = session.isPermanent ? Integer.MAX_VALUE : session.pendingDurationTicks;
        session.pendingState = TemporalState.NONE;
        activeGlobalDominantPlayer = player.getUUID();

        applyServerTickRate(server.tickRateManager(), state, session.activeAcceleratedTickRate, session.activeDilatedTickRate);

        switch (state) {
            case TIME_STOP -> {
                level.playSound(null, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.2f, 0.5f);
                level.playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.9f, 0.6f);
            }
            case ACCELERATION -> {
                level.playSound(null, player.blockPosition(), SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 1.0f, 1.8f);
                level.playSound(null, player.blockPosition(), SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 1.2f, 1.6f);
            }
            case DILATION -> {
                level.playSound(null, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.2f, 0.5f);
                level.playSound(null, player.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1.0f, 0.6f);
            }
            default -> {}
        }

        if (state.getStartMessageKey() != null) {
            player.sendSystemMessage(Component.translatable(state.getStartMessageKey()), true);
        }
    }

    private static void handleActiveStateTick(ServerPlayer player, HourglassSession session) {
        if (session.isPermanent) {
            return;
        }
        session.remainingEffectTicks--;
        if (session.remainingEffectTicks <= 0) {
            startCollapsing(player, session);
        }
    }

    private static void startCollapsing(ServerPlayer player, HourglassSession session) {
        float effectiveTps = switch (session.currentState) {
            case ACCELERATION -> session.activeAcceleratedTickRate;
            case DILATION -> session.activeDilatedTickRate;
            default -> 20.0F;
        };

        session.collapseTicks = Math.max(1, (int) (1.6F * effectiveTps));
        if (player != null) {
            Services.PLATFORM.sendTemporalEffectToPlayer(player, session.currentState.getModeId(), true);
        }
    }

    private static void handleCollapseTick(ServerPlayer player, HourglassSession session) {
        session.collapseTicks--;
        if (session.collapseTicks <= 0) {
            finishTemporalState(player, session);
        }
    }

    private static void finishTemporalState(ServerPlayer player, HourglassSession session) {
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

        session.reset(session.pendingNextCooldownTicks);
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
            ServerTickRateManager tickRateManager = server.tickRateManager();
            tickRateManager.setFrozen(false);
            tickRateManager.setTickRate(20.0F);
        }
    }

    private static int getRandomRangeTicks(double minSeconds, double maxSeconds) {
        int minTicks = (int) (Math.max(1.0, minSeconds) * 20);
        int maxTicks = (int) (Math.max(minTicks / 20.0, maxSeconds) * 20);
        return minTicks + (maxTicks > minTicks ? RANDOM.nextInt(maxTicks - minTicks + 1) : 0);
    }

    public static void cleanSession(UUID playerUUID, MinecraftServer server) {
        HourglassSession session = SESSIONS.remove(playerUUID);
        if (server == null) return;

        if (playerUUID.equals(activeGlobalDominantPlayer)) {
            activeGlobalDominantPlayer = null;
            resetServerTickRate(server);
        }

        ServerPlayer player = server.getPlayerList().getPlayer(playerUUID);
        if (player != null) {
            Services.PLATFORM.sendTemporalEffectToPlayer(player, 0, false);
            if (session != null && session.currentState != TemporalState.NONE) {
                if (player.level() instanceof ServerLevel level) {
                    playResumeEffects(level, player, session.currentState);
                }
            }
        }
    }
}
