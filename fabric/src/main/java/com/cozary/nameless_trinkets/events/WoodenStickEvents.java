package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class WoodenStickEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if(targetEntity instanceof Player player){
                if(WoodenStickHandler.cancelWoodenStick(player)){
                    return 0.0f;
                }
            }
            return damageAmount;
        });
    }
}
