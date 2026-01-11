package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.Entity;
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
        Entity attacker = event.getSource().getDirectEntity();
        if (attacker instanceof Player player) {
            if (event.getEntity() instanceof LivingEntity target) {
                RageMindHandler.getEntity(player, target);
            }
        }
    }

    @SubscribeEvent
    public static void dealDamage(LivingDamageEvent.Pre event) {
        Entity attacker = event.getSource().getDirectEntity();
        if (attacker instanceof Player player) {
            RageMindHandler.dealDamage(player, event.getEntity(), event.getOriginalDamage());
        }
    }

}
