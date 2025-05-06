package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class ExplosionProofJacketEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(targetEntity instanceof Player player){
                return ExplosionProofJacketHandler.handleExplosionDamageReduction(player, damageSource, amount);
            }
            return amount;
        });
    }
}
