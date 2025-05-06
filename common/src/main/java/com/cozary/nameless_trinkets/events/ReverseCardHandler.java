package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.ReverseCardBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class ReverseCardHandler {

    public static void reverseDamage(Player player, DamageSource damageSource, float originalDamage) {
        ReverseCardBase.Stats config = ReverseCardBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Entity src = damageSource.getEntity();
        Random random = new Random();


        if (random.nextInt(100) <= config.chanceToActivate) {

            if (src != null && !(src instanceof Player) && !player.level().isClientSide) {
                ((ServerLevel) src.getCommandSenderWorld()).sendParticles(ParticleTypes.WITCH, src.getX(), src.getY(), src.getZ(), 35, 1D, 1D, 1D, 0.1);
                src.hurt(src.damageSources().generic(), originalDamage);
            }
        }
    }

}
