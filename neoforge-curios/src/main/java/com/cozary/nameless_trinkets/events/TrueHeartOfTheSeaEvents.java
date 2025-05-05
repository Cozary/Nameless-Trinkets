package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class TrueHeartOfTheSeaEvents {

    @SubscribeEvent
    public static void function(PlayerEvent.BreakSpeed event) {

        float newSpeed = TrueHeartOfTheSeaHandler.function(event.getEntity(), event.getOriginalSpeed());
        event.setNewSpeed(newSpeed);


    }
}
