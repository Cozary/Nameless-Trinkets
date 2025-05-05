package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.TrueHeartOfTheSea;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;

public class TrueHeartOfTheSeaHandler {

    public static float function(Player player, float originalSpeed) {
        TrueHeartOfTheSea.Stats config = TrueHeartOfTheSea.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalSpeed;

        if (!player.isSpectator()) {
            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return originalSpeed;
            }
            var stack = accessories.getEquipped(ModItems.TRUE_HEART_OF_THE_SEA.get());
            boolean isInWater = player.isEyeInFluid(FluidTags.WATER);

            if ((!stack.isEmpty() && isInWater)) {
                return originalSpeed * (config.miningUnderwaterSpeedPercentage / 100);
            }
        }
        return originalSpeed;
    }
}
