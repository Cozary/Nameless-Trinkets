package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class WoundbearerEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if (targetEntity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.WOUNDBEARER.get());

                if (stack.isEmpty())
                    return damageAmount;

                WoundbearerHandler.savePlayerDamageIncrement(player, damageAmount, stack.getFirst().stack());
            }
            return damageAmount;
        });
    }
}
