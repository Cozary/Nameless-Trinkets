package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BlazeNucleus;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageTypes;
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
