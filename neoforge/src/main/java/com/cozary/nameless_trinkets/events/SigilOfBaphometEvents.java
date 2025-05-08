package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class SigilOfBaphometEvents {

    @SubscribeEvent
    public static void handleSigilKillCount(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof Player player) {
            SigilOfBaphometHandler.handleSigilKillCount(player);
        }
    }

    @SubscribeEvent
    public static void grantSigilImmunityOnDamage(LivingIncomingDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            event.setCanceled(SigilOfBaphometHandler.grantSigilImmunityOnDamage(player));

        }


    }

}
