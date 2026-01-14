package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.world.entity.player.Player;

public class VampireBloodEvents {

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity, damageSource) -> {
            if (entity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.VAMPIRE_BLOOD.get());

                if (stack.isEmpty())
                    return;

                VampireBloodHandler.function(player, killedEntity);
            }
        });
    }

}
