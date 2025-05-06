package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class AmphibiousHandsEvents {

    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {


        var stack = TrinketUtils.getEquippedTrinket(event.getEntity(), ModItems.AMPHIBIOUS_HANDS.get());

        if (stack.isEmpty())
            return;

        float newSpeed = AmphibiousHandsHandler.handleBreakSpeed(event.getEntity(), event.getOriginalSpeed());
        event.setNewSpeed(newSpeed);
    }
}