package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class BrokenAnkhEvents {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (entity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BROKEN_ANKH.get());

                if (stack.isEmpty())
                    return true;

                return !BrokenAnkhHandler.tryPreventDeath(player, stack.getFirst().stack().getItem());
            }
            return true;
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {

                    var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BROKEN_ANKH.get());

                    if (stack.isEmpty())
                        return;

                    BrokenAnkhHandler.restoreCooldownOnLogin(player, stack.getFirst().stack().getItem());
                }
            });
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {

                    var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BROKEN_ANKH.get());

                    if (stack.isEmpty())
                        return;

                    BrokenAnkhHandler.saveCooldownOnLogout(player, stack.getFirst().stack().getItem());
                }
            });
        });
    }
}
