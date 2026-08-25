package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;

public class EclipseAshesEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ECLIPSE_ASHES.get());
                if (!stack.isEmpty() && damageSource.getEntity() instanceof LivingEntity attacker) {
                    EclipseAshesHandler.onPlayerAttacked(player, attacker, stack.getFirst().stack());
                }
            }

            if (damageSource.getEntity() instanceof Wolf wolf && EclipseAshesHandler.isPhantomWolf(wolf)) {
                EclipseAshesHandler.onPhantomWolfAttack(wolf, targetEntity);
            }

            return amount;
        });
    }
}
