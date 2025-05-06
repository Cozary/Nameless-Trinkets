package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;

public class LightGlovesEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {
            return LightGlovesHandler.lightGlovesSpeedBreak(player, originalSpeed);
        });
    }

}
