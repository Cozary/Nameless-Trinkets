package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.AmphibiousHands;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;


public class AmphibiousHandsEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {
            AmphibiousHands.Stats config = AmphibiousHands.INSTANCE.getTrinketConfig();

            if (!config.isEnable) return originalSpeed;

            if (player != null && !player.isSpectator()) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.AMPHIBIOUS_HANDS.get());

                if (!stack.isEmpty() && player.isEyeInFluid(FluidTags.WATER)) {
                    return originalSpeed * config.miningUnderwaterSpeedMultiplier;
                }
            }
            return originalSpeed;
        });
    }

}