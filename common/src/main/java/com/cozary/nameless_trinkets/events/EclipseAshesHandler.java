package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.EclipseAshesBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EclipseAshesHandler {

    private static final String ECLIPSE_TEAM_NAME = "nt_eclipse_wolf";

    private static final Map<UUID, EclipseSession> SESSIONS = new ConcurrentHashMap<>();
    private static final Set<UUID> PHANTOM_WOLVES = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final Map<UUID, UUID> WOLF_OWNERS = new ConcurrentHashMap<>();

    public static class EclipseSession {
        public final UUID playerUUID;
        public UUID targetUUID;
        public int remainingTicks;
        public UUID currentWolfUUID;
        public int spawnDelayTicks;
        public int wolfAliveTicks;

        public EclipseSession(UUID playerUUID, UUID targetUUID, int remainingTicks) {
            this.playerUUID = playerUUID;
            this.targetUUID = targetUUID;
            this.remainingTicks = remainingTicks;
            this.currentWolfUUID = null;
            this.spawnDelayTicks = 0;
            this.wolfAliveTicks = 0;
        }
    }

    public static void onPlayerAttacked(Player player, LivingEntity attacker, ItemStack itemStack) {
        if (!player.level().isClientSide() && attacker != null) {
            EclipseAshesBase.Stats config = EclipseAshesBase.INSTANCE.getTrinketConfig();
            if (!config.isEnable) {
                return;
            }

            LivingEntity target = resolveActualTarget(player, attacker);
            if (target == null || target == player || !target.isAlive() || target.isRemoved() || target.isSpectator()) {
                return;
            }

            int totalTicks = (int) (config.activeDurationSeconds * 20);

            EclipseSession session = SESSIONS.get(player.getUUID());
            if (session != null) {
                session.targetUUID = target.getUUID();
                session.remainingTicks = totalTicks;
                if (session.currentWolfUUID != null && player.level() instanceof ServerLevel serverLevel) {
                    Entity wolfEntity = serverLevel.getEntity(session.currentWolfUUID);
                    if (wolfEntity instanceof Wolf wolf && wolf.isAlive()) {
                        wolf.setTarget(target);
                    }
                }
            } else {
                session = new EclipseSession(player.getUUID(), target.getUUID(), totalTicks);
                SESSIONS.put(player.getUUID(), session);

                if (player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SOUL, player.getX(), player.getY() + 1.0, player.getZ(), 20,
                            0.4, 0.4, 0.4, 0.05);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1.0, player.getZ(),
                            15, 0.4, 0.4, 0.4, 0.05);
                    serverLevel.playSound(null, player.blockPosition(), SoundEvents.SOUL_ESCAPE.value(),
                            SoundSource.PLAYERS, 1.0f, 0.85f);
                }
            }
        }
    }

    public static void tick(LivingEntity entity, ItemStack itemStack) {
        if (entity instanceof ServerPlayer player) {
            EclipseSession session = SESSIONS.get(player.getUUID());
            if (session == null) {
                return;
            }

            ServerLevel level = (ServerLevel) player.level();

            if (!player.isAlive() || player.isRemoved()) {
                cleanupSession(level, session);
                return;
            }

            if (session.remainingTicks > 0) {
                session.remainingTicks--;
            }

            if (session.remainingTicks <= 0 && session.currentWolfUUID == null) {
                SESSIONS.remove(player.getUUID());
                return;
            }

            Entity targetEntity = level.getEntity(session.targetUUID);
            if (!(targetEntity instanceof LivingEntity target) || !target.isAlive() || target.isRemoved()
                    || target.level() != level || target.distanceToSqr(player) > 64 * 64) {
                cleanupSession(level, session);
                return;
            }

            EclipseAshesBase.Stats config = EclipseAshesBase.INSTANCE.getTrinketConfig();

            if (session.currentWolfUUID != null) {
                Entity wolfEntity = level.getEntity(session.currentWolfUUID);
                if (!(wolfEntity instanceof Wolf wolf) || !wolf.isAlive() || wolf.isRemoved()) {
                    session.currentWolfUUID = null;
                    if (session.remainingTicks <= 0) {
                        SESSIONS.remove(session.playerUUID);
                    } else {
                        session.spawnDelayTicks = config.spawnIntervalTicks;
                    }
                } else {
                    session.wolfAliveTicks++;

                    if (wolf.getTarget() != target) {
                        wolf.setTarget(target);
                    }

                    if (session.wolfAliveTicks % 2 == 0) {
                        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, wolf.getX(), wolf.getY() + 0.3, wolf.getZ(),
                                2, 0.2, 0.2, 0.2, 0.02);
                    }

                    if (session.wolfAliveTicks > 80) {
                        vanishWolf(level, wolf);
                        session.currentWolfUUID = null;
                        if (session.remainingTicks <= 0) {
                            SESSIONS.remove(session.playerUUID);
                        } else {
                            session.spawnDelayTicks = config.spawnIntervalTicks;
                        }
                    }
                }
            } else {
                if (session.remainingTicks > 0) {
                    if (session.spawnDelayTicks > 0) {
                        session.spawnDelayTicks--;
                    } else {
                        spawnPhantomWolf(level, player, target, session, config);
                    }
                }
            }
        }
    }

    private static void spawnPhantomWolf(ServerLevel level, ServerPlayer player, LivingEntity target,
            EclipseSession session, EclipseAshesBase.Stats config) {
        double angle = level.getRandom().nextDouble() * 2 * Math.PI;
        double distance = 2.0 + level.getRandom().nextDouble() * 1.5;
        double spawnX = target.getX() + Math.cos(angle) * distance;
        double spawnZ = target.getZ() + Math.sin(angle) * distance;
        double spawnY = target.getY();

        Wolf wolf = EntityType.WOLF.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if (wolf == null) {
            return;
        }

        wolf.setPos(spawnX, spawnY, spawnZ);
        wolf.setAge(0);
        wolf.setInvisible(true);
        wolf.setGlowingTag(true);
        PHANTOM_WOLVES.add(wolf.getUUID());
        WOLF_OWNERS.put(wolf.getUUID(), player.getUUID());

        applyBlackGlowingTeam(level, wolf);

        var damageAttribute = wolf.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttribute != null) {
            damageAttribute.setBaseValue(config.wolfDamage);
        }

        var speedAttribute = wolf.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            speedAttribute.setBaseValue(0.45D);
        }

        wolf.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 0, false, false));
        wolf.addEffect(new MobEffectInstance(MobEffects.GLOWING, 6000, 0, false, false));
        wolf.addEffect(new MobEffectInstance(MobEffects.SPEED, 6000, 1, false, false));

        wolf.setTarget(target);

        level.sendParticles(ParticleTypes.SOUL, spawnX, spawnY + 0.5, spawnZ, 20, 0.3, 0.3, 0.3, 0.05);
        level.sendParticles(ParticleTypes.SMOKE, spawnX, spawnY + 0.5, spawnZ, 15, 0.3, 0.3, 0.3, 0.05);
        level.playSound(null, wolf.blockPosition(), SoundEvents.SOUL_ESCAPE.value(), SoundSource.NEUTRAL, 1.0f, 0.7f);

        level.addFreshEntity(wolf);

        session.currentWolfUUID = wolf.getUUID();
        session.wolfAliveTicks = 0;
    }

    public static void onPhantomWolfAttack(Wolf wolf, LivingEntity target) {
        if (!isPhantomWolf(wolf)) {
            return;
        }

        if (wolf.level() instanceof ServerLevel level) {
            vanishWolf(level, wolf);
        }

        for (EclipseSession session : SESSIONS.values()) {
            if (session.currentWolfUUID != null && session.currentWolfUUID.equals(wolf.getUUID())) {
                session.currentWolfUUID = null;

                if (session.remainingTicks <= 0 || target == null || !target.isAlive() || target.isRemoved()) {
                    SESSIONS.remove(session.playerUUID);
                } else {
                    EclipseAshesBase.Stats config = EclipseAshesBase.INSTANCE.getTrinketConfig();
                    session.spawnDelayTicks = config.spawnIntervalTicks;
                }
                break;
            }
        }
    }

    public static boolean isPhantomWolf(Entity entity) {
        if (entity == null) {
            return false;
        }
        return PHANTOM_WOLVES.contains(entity.getUUID());
    }

    private static void applyBlackGlowingTeam(ServerLevel level, Wolf wolf) {
        Scoreboard scoreboard = level.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(ECLIPSE_TEAM_NAME);
        if (team == null) {
            team = scoreboard.addPlayerTeam(ECLIPSE_TEAM_NAME);
            team.setColor(ChatFormatting.BLACK);
            team.setSeeFriendlyInvisibles(false);
        }
        scoreboard.addPlayerToTeam(wolf.getStringUUID(), team);
    }

    private static void removeBlackGlowingTeam(ServerLevel level, Wolf wolf) {
        Scoreboard scoreboard = level.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(ECLIPSE_TEAM_NAME);
        if (team != null) {
            scoreboard.removePlayerFromTeam(wolf.getStringUUID(), team);
        }
    }

    private static void vanishWolf(ServerLevel level, Wolf wolf) {
        removeBlackGlowingTeam(level, wolf);
        PHANTOM_WOLVES.remove(wolf.getUUID());
        WOLF_OWNERS.remove(wolf.getUUID());
        level.sendParticles(ParticleTypes.SOUL, wolf.getX(), wolf.getY() + 0.5, wolf.getZ(), 20, 0.3, 0.3, 0.3, 0.08);
        level.sendParticles(ParticleTypes.POOF, wolf.getX(), wolf.getY() + 0.5, wolf.getZ(), 10, 0.2, 0.2, 0.2, 0.05);
        level.playSound(null, wolf.blockPosition(), SoundEvents.SOUL_ESCAPE.value(), SoundSource.NEUTRAL, 0.8f, 1.2f);
        wolf.discard();
    }

    private static LivingEntity resolveActualTarget(Player player, LivingEntity attacker) {
        if (attacker == null) {
            return null;
        }

        // Phantom wolves
        if (isPhantomWolf(attacker)) {
            UUID ownerUUID = WOLF_OWNERS.get(attacker.getUUID());
            if (ownerUUID != null && player.level() instanceof ServerLevel serverLevel) {
                Entity owner = serverLevel.getEntity(ownerUUID);
                if (owner instanceof LivingEntity livingOwner && livingOwner != player && livingOwner.isAlive()
                        && !livingOwner.isRemoved()) {
                    return livingOwner;
                }
            }
            return null;
        }

        // Owners
        if (attacker instanceof OwnableEntity ownable) {
            LivingEntity owner = ownable.getOwner();
            if (owner != null && owner != player && owner.isAlive() && !owner.isRemoved()) {
                return owner;
            }
        }

        // Projectiles or something like that
        if (attacker instanceof TraceableEntity traceable) {
            Entity owner = traceable.getOwner();
            if (owner instanceof LivingEntity livingOwner && livingOwner != player && livingOwner.isAlive()
                    && !livingOwner.isRemoved()) {
                return livingOwner;
            }
        }

        return attacker;
    }

    private static void cleanupSession(ServerLevel level, EclipseSession session) {
        if (session.currentWolfUUID != null) {
            Entity wolfEntity = level.getEntity(session.currentWolfUUID);
            if (wolfEntity instanceof Wolf wolf && wolf.isAlive()) {
                vanishWolf(level, wolf);
            }
        }
        SESSIONS.remove(session.playerUUID);
    }
}
