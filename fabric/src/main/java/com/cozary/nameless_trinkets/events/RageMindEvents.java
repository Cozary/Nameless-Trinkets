package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RageMindEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if (damageSource.getEntity() instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.RAGE_MIND.get());

                if (stack.isEmpty())
                    return damageAmount;

                return RageMindHandler.dealDamage(player, targetEntity, damageAmount, stack.getFirst().stack().getItem());
            }
            return damageAmount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.RAGE_MIND.get());

                if (stack.isEmpty())
                    return amount;

                if (damageSource.getEntity() instanceof LivingEntity attacker) {
                    RageMindHandler.getEntity(attacker, stack.getFirst().stack().getItem());
                }
            }
            return amount;
        });
    }
}
