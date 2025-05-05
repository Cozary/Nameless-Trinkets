package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FracturedNullstone;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class FracturedNullstoneHandler {

    public static float reduceMagicDamage(Player player, DamageSource damageSource, float originalDamage) {
        FracturedNullstone.Stats config = FracturedNullstone.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalDamage;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return originalDamage;
        }
        var stack = accessories.getEquipped(ModItems.FRACTURED_NULLSTONE.get());
        if (!stack.isEmpty()) {

            //haha DamageTypeTag Magic doesn't exist

            if (damageSource.type().msgId().equals("indirectMagic") || damageSource.type().msgId().equals("magic")) {
                return originalDamage* (config.magicDamageReductionPercentage / 100);
            }
        }
        return originalDamage;
    }
}

