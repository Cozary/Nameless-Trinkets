package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class MinersSoulEvents {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, entity) -> {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.MINERS_SOUL.get());

            if (stack.isEmpty())
                return;

            MinersSoulHandler.playerBreakBlock(player, state, pos, level);
        });
    }

}
