package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class FracturedNullstoneEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.FRACTURED_NULLSTONE.get());

                if (stack.isEmpty())
                    return amount;

                return FracturedNullstoneHandler.reduceMagicDamage(damageSource, amount);
            }
            return amount;
        });
    }
}
