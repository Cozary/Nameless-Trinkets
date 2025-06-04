package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;

public class LightGlovesEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.LIGHT_GLOVES.get());

            if (stack.isEmpty())
                return originalSpeed;

            return LightGlovesHandler.lightGlovesSpeedBreak(player, originalSpeed);
        });
    }

}
