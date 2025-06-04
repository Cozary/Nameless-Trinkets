package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class PufferFishLiverEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
           if(damageSource.getEntity() instanceof Player player){

               var stack = TrinketUtils.getEquippedTrinket(player, ModItems.PUFFER_FISH_LIVER.get());

               if (stack.isEmpty())
                   return amount;

               PufferFishLiverHandler.applyPoisonEffect(player, targetEntity);
           }
            return amount;
        });
    }
}
