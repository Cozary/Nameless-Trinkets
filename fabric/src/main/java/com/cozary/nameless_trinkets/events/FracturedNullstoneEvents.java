package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FracturedNullstone;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class FracturedNullstoneEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(targetEntity instanceof Player player){
               return FracturedNullstoneHandler.reduceMagicDamage(player, damageSource, amount);
            }
            return amount;
        });
    }
}
