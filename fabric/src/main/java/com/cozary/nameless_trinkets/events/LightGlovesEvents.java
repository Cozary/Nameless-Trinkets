package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.LightGloves;
import io.wispforest.accessories.api.AccessoriesCapability;

public class LightGlovesEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {
            return LightGlovesHandler.lightGlovesSpeedBreak(player, originalSpeed);
        });
    }

}
