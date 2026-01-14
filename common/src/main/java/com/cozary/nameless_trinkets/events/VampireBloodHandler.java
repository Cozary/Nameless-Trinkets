package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.VampireBloodBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class VampireBloodHandler {

    public static void function(Player player, LivingEntity entity) {
        VampireBloodBase.Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!player.level().isClientSide) {
            ((ServerLevel) entity.level()).sendParticles(ParticleTypes.SNEEZE, entity.getX(), entity.getY(), entity.getZ(), 35, 1D, 1D, 1D, 0.1);
            player.heal(entity.getMaxHealth() * (config.healingPercentage / 100));
        }
    }

}
