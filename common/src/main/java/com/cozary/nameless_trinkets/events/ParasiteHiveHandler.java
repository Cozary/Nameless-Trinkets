package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.ParasiteHiveBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParasiteHiveHandler {

    private static final Set<UUID> PARASITE_SILVERFISH = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final Map<UUID, UUID> SILVERFISH_OWNERS = new ConcurrentHashMap<>();
    private static final Map<UUID, UUID> SILVERFISH_TARGETS = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> SILVERFISH_SPAWN_TICKS = new ConcurrentHashMap<>();

    public static void onPlayerAttacked(Player player, LivingEntity attacker) {
        if (player.level().isClientSide() || attacker == null) {
            return;
        }

        ParasiteHiveBase.Stats config = ParasiteHiveBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            return;
        }

        if (player.getRandom().nextFloat() * 100.0F > config.chanceToActivate) {
            return;
        }

        LivingEntity target = resolveActualTarget(player, attacker);
        if (target == null || target == player || !target.isAlive() || target.isRemoved() || target.isSpectator()) {
            return;
        }

        ServerLevel level = (ServerLevel) player.level();
        long currentGameTime = level.getGameTime();
        int countToSpawn = config.minSilverfish + level.getRandom().nextInt(Math.max(1, config.maxSilverfish - config.minSilverfish + 1));

        for (int spawnIndex = 0; spawnIndex < countToSpawn; spawnIndex++) {
            Silverfish silverfish = EntityTypes.SILVERFISH.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
            if (silverfish == null) {
                continue;
            }

            double angle = level.getRandom().nextDouble() * 2 * Math.PI;
            double distance = 0.8 + level.getRandom().nextDouble() * 1.2;
            double spawnX = player.getX() + Math.cos(angle) * distance;
            double spawnZ = player.getZ() + Math.sin(angle) * distance;
            double spawnY = player.getY() + 0.1;

            silverfish.setPos(spawnX, spawnY, spawnZ);

            var damageAttribute = silverfish.getAttribute(Attributes.ATTACK_DAMAGE);
            if (damageAttribute != null) {
                damageAttribute.setBaseValue(config.silverfishDamage);
            }

            var speedAttribute = silverfish.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute != null) {
                speedAttribute.setBaseValue(0.35D);
            }

            silverfish.targetSelector.removeAllGoals(goal -> true);

            silverfish.goalSelector.removeAllGoals(goal -> !(goal instanceof FloatGoal || goal instanceof ClimbOnTopOfPowderSnowGoal));
            silverfish.goalSelector.addGoal(1, new FloatGoal(silverfish));
            silverfish.goalSelector.addGoal(2, new MeleeAttackGoal(silverfish, 1.4D, true));

            silverfish.setTarget(target);
            silverfish.getNavigation().moveTo(target, 1.4D);

            PARASITE_SILVERFISH.add(silverfish.getUUID());
            SILVERFISH_OWNERS.put(silverfish.getUUID(), player.getUUID());
            SILVERFISH_TARGETS.put(silverfish.getUUID(), target.getUUID());
            SILVERFISH_SPAWN_TICKS.put(silverfish.getUUID(), currentGameTime);

            level.sendParticles(ParticleTypes.POOF, spawnX, spawnY + 0.2, spawnZ, 5, 0.15, 0.15, 0.15, 0.05);
            level.playSound(null, silverfish.blockPosition(), SoundEvents.SILVERFISH_HURT, SoundSource.HOSTILE, 0.7f, 1.2f);

            level.addFreshEntity(silverfish);
        }
    }

    public static void onParasiteSilverfishAttack(Silverfish silverfish) {
        if (silverfish.level() instanceof ServerLevel level) {
            vanishSilverfish(level, silverfish);
        }
    }

    public static boolean isParasiteSilverfish(Entity entity) {
        if (entity == null) {
            return false;
        }
        return PARASITE_SILVERFISH.contains(entity.getUUID());
    }

    public static boolean isOwner(Silverfish silverfish, Entity entity) {
        if (silverfish == null || entity == null) {
            return false;
        }
        UUID ownerUUID = SILVERFISH_OWNERS.get(silverfish.getUUID());
        return ownerUUID != null && ownerUUID.equals(entity.getUUID());
    }

    public static void tick(LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        ParasiteHiveBase.Stats config = ParasiteHiveBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            return;
        }

        ServerLevel level = (ServerLevel) player.level();
        long currentGameTime = level.getGameTime();

        if (player.getRandom().nextFloat() < config.ambientSoundChance) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SILVERFISH_AMBIENT, SoundSource.PLAYERS, 0.6f, 0.8f + player.getRandom().nextFloat() * 0.4f);
        }

        Iterator<Map.Entry<UUID, UUID>> iterator = SILVERFISH_OWNERS.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, UUID> entry = iterator.next();
            if (entry.getValue().equals(player.getUUID())) {
                UUID silverfishUUID = entry.getKey();
                Entity silverfishEntity = level.getEntity(silverfishUUID);

                if (!(silverfishEntity instanceof Silverfish silverfish) || !silverfish.isAlive() || silverfish.isRemoved()) {
                    iterator.remove();
                    PARASITE_SILVERFISH.remove(silverfishUUID);
                    SILVERFISH_TARGETS.remove(silverfishUUID);
                    SILVERFISH_SPAWN_TICKS.remove(silverfishUUID);
                } else {
                    Long spawnTick = SILVERFISH_SPAWN_TICKS.get(silverfishUUID);
                    boolean isExpired = (spawnTick != null && (currentGameTime - spawnTick) > config.silverfishLifetimeTicks);

                    UUID targetUUID = SILVERFISH_TARGETS.get(silverfishUUID);
                    Entity targetEntity = targetUUID != null ? level.getEntity(targetUUID) : null;
                    boolean hasInvalidTarget = !(targetEntity instanceof LivingEntity target) || !target.isAlive() || target.isRemoved() || target.level() != level || target.distanceToSqr(player) > 64 * 64;

                    if (isExpired || hasInvalidTarget) {
                        vanishSilverfish(level, silverfish);
                        iterator.remove();
                    } else {
                        LivingEntity target = (LivingEntity) targetEntity;
                        if (silverfish.getTarget() == player || silverfish.getTarget() == null || silverfish.getTarget() != target) {
                            silverfish.setTarget(target);
                        }

                        boolean inMeleeRange = silverfish.getBoundingBox().inflate(0.75D, 0.5D, 0.75D).intersects(target.getBoundingBox())
                                || silverfish.distanceToSqr(target) <= 4.0D;

                        if (inMeleeRange) {
                            silverfish.doHurtTarget(level, target);
                            if (!silverfish.isRemoved()) {
                                vanishSilverfish(level, silverfish);
                            }
                            iterator.remove();
                        } else {
                            if (silverfish.tickCount % 5 == 0 || silverfish.getNavigation().isDone()) {
                                silverfish.getNavigation().moveTo(target, 1.4D);
                            }
                            if (silverfish.distanceToSqr(target) <= 9.0D && silverfish.onGround()) {
                                Vec3 directionToTarget = target.position().subtract(silverfish.position()).normalize().scale(0.35D);
                                silverfish.setDeltaMovement(silverfish.getDeltaMovement().add(directionToTarget.x, 0.15D, directionToTarget.z));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void vanishSilverfish(ServerLevel level, Silverfish silverfish) {
        if (silverfish.isRemoved()) {
            return;
        }
        PARASITE_SILVERFISH.remove(silverfish.getUUID());
        SILVERFISH_OWNERS.remove(silverfish.getUUID());
        SILVERFISH_TARGETS.remove(silverfish.getUUID());
        SILVERFISH_SPAWN_TICKS.remove(silverfish.getUUID());

        level.sendParticles(ParticleTypes.POOF, silverfish.getX(), silverfish.getY() + 0.2, silverfish.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        level.playSound(null, silverfish.blockPosition(), SoundEvents.SILVERFISH_DEATH, SoundSource.HOSTILE, 0.9f, 1.2f);
        silverfish.discard();
    }

    private static LivingEntity resolveActualTarget(Player player, LivingEntity attacker) {
        if (attacker == null || attacker == player) {
            return null;
        }

        if (isParasiteSilverfish(attacker)) {
            return null;
        }

        if (attacker instanceof OwnableEntity ownable && ownable.getOwner() == player) {
            return null;
        }

        if (attacker instanceof TraceableEntity traceable && traceable.getOwner() == player) {
            return null;
        }

        return attacker;
    }
}
