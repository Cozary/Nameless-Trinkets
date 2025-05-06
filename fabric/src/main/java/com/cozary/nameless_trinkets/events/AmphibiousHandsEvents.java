package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;


public class AmphibiousHandsEvents {
    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) ->
                AmphibiousHandsHandler.handleBreakSpeed(player, originalSpeed)
        );
    }
}
