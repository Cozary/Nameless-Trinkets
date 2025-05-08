package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class RageMindEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if (damageSource.getEntity() instanceof Player player) {
                return RageMindHandler.dealDamage(player, targetEntity, damageAmount);
            }
            return damageAmount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (damageSource.getEntity() instanceof Player player) {
                RageMindHandler.getEntity(player, targetEntity);
            }
            return amount;
        });
    }
}
