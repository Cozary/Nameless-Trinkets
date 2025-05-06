package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.AmphibiousHandsBase;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;

public class AmphibiousHandsHandler {

    public static float handleBreakSpeed(Player player, float originalSpeed) {
        AmphibiousHandsBase.Stats config = AmphibiousHandsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable || player == null || player.isSpectator()) {
            return originalSpeed;
        }


        boolean isInWater = player.isEyeInFluid(FluidTags.WATER);

        if (isInWater) {
            return originalSpeed * (config.miningUnderwaterSpeedPercentage / 100);
        }

        return originalSpeed;
    }
}
