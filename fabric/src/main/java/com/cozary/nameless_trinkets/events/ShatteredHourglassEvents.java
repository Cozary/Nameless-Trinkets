package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ShatteredHourglassEvents {

    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (handler.player != null) {
                ShatteredHourglassHandler.cleanSession(handler.player.getUUID(), server);
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (newPlayer != null) {
                ShatteredHourglassHandler.cleanSession(newPlayer.getUUID(), newPlayer.level().getServer());
            }
        });
    }
}
