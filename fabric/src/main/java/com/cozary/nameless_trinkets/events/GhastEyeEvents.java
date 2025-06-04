package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.player.Player;

public class GhastEyeEvents {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
           if(damageSource.getEntity() instanceof Player player){

               var stack = TrinketUtils.getEquippedTrinket(player, ModItems.GHAST_EYE.get());

               if (stack.isEmpty())
                   return true;

               GhastEyeHandler.obtainRegenOnKill(player);
           }
            return true;
        });
    }

}
