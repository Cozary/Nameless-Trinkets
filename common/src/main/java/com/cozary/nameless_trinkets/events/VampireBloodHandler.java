package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.VampireBlood;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class VampireBloodHandler {

    public static void function(Player player, LivingEntity entity) {
        VampireBlood.Stats config = VampireBlood.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }
        var stack = accessories.getEquipped(ModItems.VAMPIRE_BLOOD.get());
        if (!stack.isEmpty() && !player.level().isClientSide) {
            ((ServerLevel) entity.getCommandSenderWorld()).sendParticles(ParticleTypes.SNEEZE, entity.getX(), entity.getY(), entity.getZ(), 35, 1D, 1D, 1D, 0.1);
            player.heal(entity.getMaxHealth() * (config.healingPercentage / 100));
        }
    }

}
