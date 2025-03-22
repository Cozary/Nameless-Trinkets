package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.MissingPage;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class MissingPageEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            MissingPage.Stats config = MissingPage.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.MISSING_PAGE.get());

                Random random = new Random();

                if (!stack.isEmpty() && random.nextInt(100) <= config.activationPercentage) {

                    AABB targetBox = new AABB(player.position(), player.position()).inflate(config.radiusInBlocks);

                    List<LivingEntity> foundTarget =
                            targetEntity.level().getEntitiesOfClass(LivingEntity.class, targetBox, MissingPageEvents::isValidTarget);

                    if (damageSource.getEntity() != null && !(damageSource.getEntity() instanceof Player) && !foundTarget.isEmpty() && !player.level().isClientSide) {

                        for (LivingEntity livingEntity : foundTarget) {
                            ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.SOUL, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 35, 1D, 1D, 1D, 0.1);
                            livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.percentageOfDamage / 100));
                        }
                    }
                }
            }

            return amount;
        });
    }


    private static boolean isValidTarget(LivingEntity ent) {
        return (ent.getType() != EntityType.PLAYER) && (!ent.isInvulnerable());
    }

}
