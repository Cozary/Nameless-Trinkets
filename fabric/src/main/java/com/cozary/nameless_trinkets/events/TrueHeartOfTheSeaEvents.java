package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.TrueHeartOfTheSea;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;

public class TrueHeartOfTheSeaEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {
           return TrueHeartOfTheSeaHandler.function(player, originalSpeed);
        });
    }
}
