package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class MinersSoulEvents {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, entity) -> {
            MinersSoulHandler.playerBreakBlock(player, state, pos, level);
        });
    }

}
