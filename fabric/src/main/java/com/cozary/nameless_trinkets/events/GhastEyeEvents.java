package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.player.Player;

public class GhastEyeEvents {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (damageSource.getEntity() instanceof Player player) {
                GhastEyeHandler.obtainRegenOnKill(player);
            }
            return true;
        });
    }

}
