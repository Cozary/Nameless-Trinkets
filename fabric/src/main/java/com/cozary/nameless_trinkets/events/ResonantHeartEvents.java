package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class ResonantHeartEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if(targetEntity instanceof Player player){
                ResonantHeartHandler.heartSonicBoom(player, damageSource);
            }
            return damageAmount;
        });
    }
}
