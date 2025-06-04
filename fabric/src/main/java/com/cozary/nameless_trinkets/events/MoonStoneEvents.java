package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class MoonStoneEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
           if(targetEntity instanceof Player player){

               var stack = TrinketUtils.getEquippedTrinket(player, ModItems.MOON_STONE.get());

               if (stack.isEmpty())
                   return amount;

               if (MoonStoneHandler.moonStoneFallDamage(player, damageSource)){
                   return 0.0f;
               }
           }
            return amount;
        });
    }
}
