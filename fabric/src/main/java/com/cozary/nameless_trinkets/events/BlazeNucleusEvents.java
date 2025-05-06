package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BlazeNucleusEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            Entity sourceEntity = damageSource.getEntity();
            return BlazeNucleusHandler.onAttackerHit(sourceEntity, targetEntity, amount);
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                return BlazeNucleusHandler.onPlayerHurt(player, damageSource, amount);
            }
            return amount;
        });
    }
}
