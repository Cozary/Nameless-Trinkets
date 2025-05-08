package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class MoonStoneEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                if (MoonStoneHandler.moonStoneFallDamage(player, damageSource)) {
                    return 0.0f;
                }
            }
            return amount;
        });
    }
}
