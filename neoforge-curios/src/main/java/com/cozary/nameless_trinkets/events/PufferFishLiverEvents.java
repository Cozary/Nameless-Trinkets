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

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();

        if (src instanceof Player player) {

           PufferFishLiverHandler.applyPoisonEffect(player, event.getEntity());
        }

    }
}
