package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import io.wispforest.accessories.api.AccessoriesCapability;
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
