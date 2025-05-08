package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ScarabAmuletEvent {

    @SubscribeEvent
    public static void preventHuskTargeting(LivingChangeTargetEvent event) {

        if (event.getOriginalAboutToBeSetTarget() instanceof Player player) {

            if (ScarabAmuletHandler.shouldPreventHuskTargeting(player, event.getEntity())) {
                event.setNewAboutToBeSetTarget(null);

            }

        }
    }

}
