package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class MissingPageEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                if (amount > 0) {
                    MissingPageHandler.triggerDamageReflection(player, damageSource, targetEntity);
                }
            }
            return amount;
        });
    }
}
