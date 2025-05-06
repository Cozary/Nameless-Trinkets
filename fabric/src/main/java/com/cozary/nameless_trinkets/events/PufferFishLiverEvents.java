package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class PufferFishLiverEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
           if(damageSource.getEntity() instanceof Player player){
               PufferFishLiverHandler.applyPoisonEffect(player, targetEntity);
           }
            return amount;
        });
    }
}
