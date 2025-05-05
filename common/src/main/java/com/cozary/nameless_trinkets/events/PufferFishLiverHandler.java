package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.PufferFishLiver;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PufferFishLiverHandler {

    public static void applyPoisonEffect(Player player, LivingEntity livingEntity) {
        PufferFishLiver.Stats config = PufferFishLiver.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Random random = new Random();

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }
            var stack = accessories.getEquipped(ModItems.PUFFER_FISH_LIVER.get());
            if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToApplyPoison) {
                MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.POISON, config.poisonTime, config.poisonLevel);
                livingEntity.addEffect(effectinstance);
            }
        }
}
