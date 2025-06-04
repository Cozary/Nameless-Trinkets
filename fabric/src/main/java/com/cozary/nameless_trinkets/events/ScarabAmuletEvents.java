package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class ScarabAmuletEvents {

    public static void register() {
        ModEvents.TargetingCallback.EVENT.register((attacker, target) -> {
            if(target instanceof Player player){

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.SCARAB_AMULET.get());

                if (stack.isEmpty())
                    return true;

                return ScarabAmuletHandler.shouldPreventHuskTargeting(player, attacker);
            }
            return true;
        });
    }
}
