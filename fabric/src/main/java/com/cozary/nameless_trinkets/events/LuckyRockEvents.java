package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class LuckyRockEvents {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.LUCKY_ROCK.get());

            if (stack.isEmpty())
                return;

            LuckyRockHandler.function(player, state, pos);
        });
    }

}
