package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.AmphibiousHands;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;

public class AmphibiousHandsHandler {

    public static float handleBreakSpeed(Player player, float originalSpeed) {
        AmphibiousHands.Stats config = AmphibiousHands.INSTANCE.getTrinketConfig();

        if (!config.isEnable || player == null || player.isSpectator()) {
            return originalSpeed;
        }

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return originalSpeed;
        }

        var stack = accessories.getEquipped(ModItems.AMPHIBIOUS_HANDS.get());

        boolean isInWater = player.isEyeInFluid(FluidTags.WATER);

        if (!stack.isEmpty() && isInWater) {
            return originalSpeed * (config.miningUnderwaterSpeedPercentage / 100);
        }

        return originalSpeed;
    }
}
