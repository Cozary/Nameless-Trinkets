package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.LightGloves;
import io.wispforest.accessories.api.AccessoriesCapability;

public class LightGlovesEvents {

    public static void register() {
        ModEvents.BlockDestroySpeedCallback.EVENT.register((player, state, originalSpeed) -> {
            LightGloves.Stats config = LightGloves.INSTANCE.getTrinketConfig();

            if (!config.isEnable) return originalSpeed;

            if (player != null && !player.isSpectator()) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.LIGHT_GLOVES.get());

                if ((!stack.isEmpty() && !player.level().canSeeSky(player.blockPosition()))) {
                    return originalSpeed * config.miningSpeedMultiplier;
                }
            }
            return originalSpeed;
        });
    }

}
