package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;

public class TrueHeartOfTheSeaEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.TRUE_HEART_OF_THE_SEA.get());

            if (stack.isEmpty())
                return originalSpeed;

            return TrueHeartOfTheSeaHandler.function(player, originalSpeed);
        });
    }
}
