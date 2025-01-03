package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.PufferFishLiver;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Random;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class PufferFishLiverEvents {

    @SubscribeEvent
    public static void applyPoisonEffect(LivingDamageEvent.Post event) {
        PufferFishLiver.Stats config = PufferFishLiver.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();
        Random random = new Random();

        if (src instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.PUFFER_FISH_LIVER.get());

            if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToApplyPoison) {
                MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.POISON, config.poisonTime, config.poisonLevel);
                LivingEntity potionGo = event.getEntity();
                potionGo.addEffect(effectinstance);
            }
        }

    }
}
