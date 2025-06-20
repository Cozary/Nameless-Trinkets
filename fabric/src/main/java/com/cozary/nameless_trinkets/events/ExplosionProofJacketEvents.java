package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class ExplosionProofJacketEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.EXPLOSION_PROOF_JACKET.get());

                if (stack.isEmpty())
                    return amount;

                return ExplosionProofJacketHandler.handleExplosionDamageReduction(player, damageSource, amount);
            }
            return amount;
        });
    }
}
