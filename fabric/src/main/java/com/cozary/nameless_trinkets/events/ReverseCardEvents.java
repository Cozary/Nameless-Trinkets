package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ReverseCard;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class ReverseCardEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            ReverseCard.Stats config = ReverseCard.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return damageAmount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {
                Random random = new Random();
                Entity sourceEntity = damageSource.getEntity();

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return damageAmount;
                }
                var stack = accessories.getEquipped(ModItems.REVERSE_CARD.get());

                if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToActivate) {

                    if (sourceEntity != null && !(sourceEntity instanceof Player) && !player.level().isClientSide) {
                        ((ServerLevel) sourceEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.WITCH, sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(), 35, 1D, 1D, 1D, 0.1);
                        sourceEntity.hurt(sourceEntity.damageSources().generic(), damageAmount);
                    }
                }
            }

            return damageAmount;
        });
    }
}
