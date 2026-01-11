package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class WoodenStickEvents {

    @SubscribeEvent
    public static void cancelWoodenStick(LivingIncomingDamageEvent event) {
        if (event.getAmount() > 0 && event.getEntity() instanceof Player player) {
            if (WoodenStickHandler.cancelWoodenStick(player)) {
                event.setCanceled(true);
            }
        }
    }
}
