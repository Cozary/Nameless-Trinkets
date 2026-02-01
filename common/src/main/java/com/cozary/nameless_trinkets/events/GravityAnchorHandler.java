package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.GravityAnchorBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GravityAnchorHandler {

    /**
     * Reduces knockback when player is hit while wearing Gravity Anchor
     * @param originalKnockback The original knockback vector
     * @return The reduced knockback vector
     */
    public static Vec3 onKnockback(Vec3 originalKnockback) {
        GravityAnchorBase.Stats config = GravityAnchorBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return originalKnockback;
        }

        double reduction = 1.0 - config.knockbackResistance;
        return originalKnockback.scale(reduction);
    }

    /**
     * Creates a seismic slam when player lands with fall damage
     * @param player The player who landed
     * @param fallDistance The distance fallen
     * @param damageSource The damage source for the AOE damage
     */
    public static void onFallDamage(Player player, float fallDistance, DamageSource damageSource) {
        GravityAnchorBase.Stats config = GravityAnchorBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return;
        }

        if (fallDistance < config.minFallDistance) {
            return;
        }

        // Calculate AOE damage and radius
        float aoeDamage = (fallDistance - 3) * config.damageMultiplier;
        double radius = Math.min(config.maxAoeRadius, fallDistance / 3.0);

        // Get all entities in range
        AABB aoe = player.getBoundingBox().inflate(radius);
        List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, aoe, 
                entity -> entity != player && entity.isAlive());

        // Apply damage to all entities in range
        for (LivingEntity entity : entities) {
            double distance = entity.distanceTo(player);
            if (distance <= radius) {
                // Damage falls off with distance
                float finalDamage = aoeDamage * (float)(1.0 - (distance / (radius + 1)));
                entity.hurt(damageSource, finalDamage);
                
                // Apply small knockback away from player
                Vec3 knockbackDir = entity.position().subtract(player.position()).normalize();
                entity.push(knockbackDir.x * 0.5, 0.2, knockbackDir.z * 0.5);
            }
        }

        // Spawn particles and play sound
        if (player.level() instanceof ServerLevel serverLevel) {
            // Spawn ground impact particles
            for (int i = 0; i < 20; i++) {
                double offsetX = (player.getRandom().nextDouble() - 0.5) * radius * 2;
                double offsetZ = (player.getRandom().nextDouble() - 0.5) * radius * 2;
                serverLevel.sendParticles(
                        ParticleTypes.CLOUD,
                        player.getX() + offsetX,
                        player.getY() + 0.1,
                        player.getZ() + offsetZ,
                        1, 0, 0.1, 0, 0.05
                );
            }

            // Play impact sound
            serverLevel.playSound(null, player.blockPosition(), 
                    SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 
                    0.5F, 0.5F);
        }
    }

    /**
     * Returns the movement speed modifier (penalty) for the player
     * @return The speed modifier (negative value)
     */
    public static double getMovementSpeedModifier() {
        GravityAnchorBase.Stats config = GravityAnchorBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return 0.0;
        }

        return -config.movementPenalty;
    }

}
