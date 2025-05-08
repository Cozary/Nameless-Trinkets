package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ScarabAmulet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;

public class ScarabAmuletHandler {

    public static boolean shouldPreventHuskTargeting(Player player, Entity attacker) {
        ScarabAmulet.Stats config = ScarabAmulet.INSTANCE.getTrinketConfig();
        if (!config.isEnable) return false;
        if (player.level().isClientSide) return false;

        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) return false;

        var stack = accessories.getEquipped(ModItems.SCARAB_AMULET.get());
        return !stack.isEmpty() && attacker instanceof Husk;
    }
}

