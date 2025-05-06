package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.player.Player;

public class FourLeafCloverEvents {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if(!(entity instanceof Player) && damageSource.getEntity() instanceof Player player){
                FourLeafCloverHandler.entityKilled(player, entity);
            }
        });
    }
}
