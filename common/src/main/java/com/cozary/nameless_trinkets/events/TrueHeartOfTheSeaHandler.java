package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.TrueHeartOfTheSeaBase;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;

public class TrueHeartOfTheSeaHandler {

    public static float function(Player player, float originalSpeed) {
        TrueHeartOfTheSeaBase.Stats config = TrueHeartOfTheSeaBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalSpeed;

        if (!player.isSpectator()) {

            boolean isInWater = player.isEyeInFluid(FluidTags.WATER);

            if ((isInWater)) {
                return originalSpeed * (config.miningUnderwaterSpeedPercentage / 100);
            }
        }
        return originalSpeed;
    }
}
