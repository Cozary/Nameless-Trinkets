package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class DarkNelumboEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player){
                if(DarkNelumboHandler.blazeNucleusImmune(player, damageSource))
                    return 0;
            }
            return amount;
        });
    }
}
