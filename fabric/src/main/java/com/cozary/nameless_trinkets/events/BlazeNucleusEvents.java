package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BlazeNucleusEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            Entity sourceEntity = damageSource.getEntity();

            var stack = TrinketUtils.getEquippedTrinket((Player) sourceEntity, ModItems.BLAZE_NUCLEUS.get());

            if (stack.isEmpty())
                return amount;

            return BlazeNucleusHandler.onAttackerHit(sourceEntity, targetEntity, amount);
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BLAZE_NUCLEUS.get());

                if (stack.isEmpty())
                    return amount;

                return BlazeNucleusHandler.onPlayerHurt(player, damageSource, amount);
            }
            return amount;
        });
    }
}
