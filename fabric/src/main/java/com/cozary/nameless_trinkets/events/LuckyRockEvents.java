package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class LuckyRockEvents {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            LuckyRockHandler.function(player, state, pos);
        });
    }

}
