package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.SleepingPillsBase;
import net.minecraft.world.entity.player.Player;

public class SleepingPillsHandler {

    public static Player.BedSleepingProblem preventSleep(Player player) {
        SleepingPillsBase.Stats config = SleepingPillsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return null;

        if (!player.isSpectator()) {


            if (config.bedDisabled) {
                return Player.BedSleepingProblem.OTHER_PROBLEM;
            }
        }
        return null;
    }

}
