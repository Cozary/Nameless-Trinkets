package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.MoonStone;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class MoonStoneHandler {

    public static boolean moonStoneFallDamage(Player player, DamageSource damageSource) {
        MoonStone.Stats config = MoonStone.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return false;
        }
        var stack = accessories.getEquipped(ModItems.MOON_STONE.get());
        if (!stack.isEmpty()) {
            return damageSource.is(DamageTypes.FALL);
        }
        return false;
    }
}

