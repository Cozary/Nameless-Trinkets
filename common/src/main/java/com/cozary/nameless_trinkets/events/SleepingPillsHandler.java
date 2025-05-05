package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SleepingPills;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class SleepingPillsHandler {

    public static Player.BedSleepingProblem preventSleep(Player player) {
        SleepingPills.Stats config = SleepingPills.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return null;

        if (!player.isSpectator()) {

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return null;
            }
            var stack = accessories.getEquipped(ModItems.SLEEPING_PILLS.get());
            if (!stack.isEmpty() && config.bedDisabled) {
                return Player.BedSleepingProblem.OTHER_PROBLEM;
            }
        }
        return null;
    }

}
