package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.LightGloves;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class LightGlovesHandler {

    public static float lightGlovesSpeedBreak(Player player, float originalSpeed) {
        LightGloves.Stats config = LightGloves.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalSpeed;

        if (!player.isSpectator()) {

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return originalSpeed;
            }
            var stack = accessories.getEquipped(ModItems.LIGHT_GLOVES.get());
            if ((!stack.isEmpty() && !player.level().canSeeSky(player.blockPosition()))) {
                return originalSpeed * (config.miningSpeedPercentage / 100);
            }

        }
        return originalSpeed;
    }
}
