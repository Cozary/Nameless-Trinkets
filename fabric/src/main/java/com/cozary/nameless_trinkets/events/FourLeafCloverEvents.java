package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.entity.player.Player;

public class FourLeafCloverEvents {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity instanceof Player) && damageSource.getEntity() instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.FOUR_LEAF_CLOVER.get());

                if (stack.isEmpty())
                    return;

                FourLeafCloverHandler.entityKilled(player, entity);
            }
        });
    }
}
