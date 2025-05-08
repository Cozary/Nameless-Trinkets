package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class ScarabAmuletEvents {

    public static void register() {
        ModEvents.TargetingCallback.EVENT.register((attacker, target) -> {
            if (target instanceof Player player) {
                if (ScarabAmuletHandler.shouldPreventHuskTargeting(player, attacker))
                    return false;
            }
            return true;
        });
    }
}
