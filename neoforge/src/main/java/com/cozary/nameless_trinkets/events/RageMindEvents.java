package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class RageMindEvents {

    @SubscribeEvent
    public static void getEntity(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            RageMindHandler.getEntity(player, (LivingEntity) event.getSource().getEntity());
        }
    }

    @SubscribeEvent
    public static void dealDamage(LivingDamageEvent.Pre event) {

        if (event.getSource().getEntity() instanceof Player player) {

            RageMindHandler.dealDamage(player, event.getEntity(), event.getOriginalDamage());
        }
    }

}
