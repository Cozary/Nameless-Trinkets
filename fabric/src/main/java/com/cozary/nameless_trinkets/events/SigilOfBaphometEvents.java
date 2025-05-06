package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.world.entity.player.Player;


public class SigilOfBaphometEvents {

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof Player player){
                SigilOfBaphometHandler.handleSigilKillCount(player);
            }
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if (targetEntity instanceof Player player) {
                if(SigilOfBaphometHandler.grantSigilImmunityOnDamage(player)){
                    return 0.0f;
                }
            }
            return damageAmount;
        });

    }

}
