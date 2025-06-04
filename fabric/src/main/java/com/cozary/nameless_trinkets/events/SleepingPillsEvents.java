package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;


public class SleepingPillsEvents {

    public static void register() {
        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.SLEEPING_PILLS.get());

            if (stack.isEmpty())
                return null;

            return SleepingPillsHandler.preventSleep(player);
        });
    }

}
