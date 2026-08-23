package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;

public class LunarCrestEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (damageSource.getEntity() instanceof Player player) {
                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.LUNAR_CREST.get());
                if (!stack.isEmpty()) {
                    return LunarCrestHandler.dealDamage(targetEntity, damageSource, amount, player, stack.getFirst().stack());
                }
            } else if (damageSource.getEntity() instanceof Wolf wolf && wolf.isTame() && wolf.getOwner() instanceof Player owner) {
                var stack = TrinketUtils.getEquippedTrinket(owner, ModItems.LUNAR_CREST.get());
                if (!stack.isEmpty()) {
                    return LunarCrestHandler.dealWolfDamage(targetEntity, damageSource, amount, wolf, owner, stack.getFirst().stack());
                }
            }
            return amount;
        });
    }
}
