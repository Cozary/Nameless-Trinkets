package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.world.entity.player.Player;

public class VampireBloodEvents {

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if(entity instanceof Player player){
                VampireBloodHandler.function(player, killedEntity);
            }
        });
    }

}
