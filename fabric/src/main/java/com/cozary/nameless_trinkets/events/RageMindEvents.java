package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class RageMindEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            Entity attacker = damageSource.getDirectEntity();
            if (attacker instanceof Player player) {
                return RageMindHandler.dealDamage(player, targetEntity, damageAmount);
            }
            return damageAmount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            Entity attacker = damageSource.getDirectEntity();
            if (attacker instanceof Player player) {
                RageMindHandler.getEntity(player, targetEntity);
            }
            return amount;
        });
    }
}
