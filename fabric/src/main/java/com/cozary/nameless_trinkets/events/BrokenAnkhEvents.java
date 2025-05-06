package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class BrokenAnkhEvents {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (entity instanceof Player player) {
                return !BrokenAnkhHandler.tryPreventDeath(player);
            }
            return true;
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {
                    BrokenAnkhHandler.restoreCooldownOnLogin(player);
                }
            });
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {
                    BrokenAnkhHandler.saveCooldownOnLogout(player);
                }
            });
        });
    }
}
