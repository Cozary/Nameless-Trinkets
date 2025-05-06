package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.PufferFishLiverBase;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PufferFishLiverHandler {

    public static void applyPoisonEffect(Player player, LivingEntity livingEntity) {
        PufferFishLiverBase.Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Random random = new Random();


        if (random.nextInt(100) <= config.chanceToApplyPoison) {
            MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.POISON, config.poisonTime, config.poisonLevel);
            livingEntity.addEffect(effectinstance);
        }
    }
}
