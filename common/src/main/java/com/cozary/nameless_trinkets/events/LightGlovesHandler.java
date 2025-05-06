package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.LightGlovesBase;
import net.minecraft.world.entity.player.Player;

public class LightGlovesHandler {

    public static float lightGlovesSpeedBreak(Player player, float originalSpeed) {
        LightGlovesBase.Stats config = LightGlovesBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalSpeed;

        if (!player.isSpectator()) {


            if (!player.level().canSeeSky(player.blockPosition())) {
                return originalSpeed * (config.miningSpeedPercentage / 100);
            }

        }
        return originalSpeed;
    }
}
