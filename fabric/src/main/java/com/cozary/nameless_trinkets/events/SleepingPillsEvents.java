package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;


public class SleepingPillsEvents {

    public static void register() {
        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> {
            return SleepingPillsHandler.preventSleep(player);
        });
    }

}
