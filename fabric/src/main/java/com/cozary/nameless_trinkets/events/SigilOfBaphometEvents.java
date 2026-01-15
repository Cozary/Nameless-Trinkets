package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.world.entity.player.Player;


public class SigilOfBaphometEvents {

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.SIGIL_OF_BAPHOMET.get());

                if (stack.isEmpty())
                    return;

                SigilOfBaphometHandler.handleSigilKillCount(stack.getFirst().stack());
            }
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            if (targetEntity instanceof Player player) {

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.SIGIL_OF_BAPHOMET.get());

                if (stack.isEmpty())
                    return damageAmount;

                if (SigilOfBaphometHandler.grantSigilImmunityOnDamage(player, stack.getFirst().stack())) {
                    return 0.0f;
                }
            }
            return damageAmount;
        });

    }

}
