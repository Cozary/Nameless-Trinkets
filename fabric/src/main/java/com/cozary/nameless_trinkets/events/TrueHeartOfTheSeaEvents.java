package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.TrueHeartOfTheSea;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;

public class TrueHeartOfTheSeaEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {
            TrueHeartOfTheSea.Stats config = TrueHeartOfTheSea.INSTANCE.getTrinketConfig();

            if (!config.isEnable) return originalSpeed;

            if (player != null && !player.isSpectator()) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.TRUE_HEART_OF_THE_SEA.get());

                if (!stack.isEmpty() && player.isEyeInFluid(FluidTags.WATER)) {
                    return originalSpeed * config.miningUnderwaterSpeed;
                }
            }
            return originalSpeed;
        });
    }
}
