package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SigilOfBaphomet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.world.entity.player.Player;


public class SigilOfBaphometEvents {

    public static void register(){
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();

            if (!config.isEnable)
                return;

            if (entity instanceof Player player && !player.isSpectator()) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());

                if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) <= 10) {
                    stack.getFirst().stack().set(ModDataComponents.SIGIL_COUNT.get(), stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) + 1);
                }
            }
        });
    }

}
