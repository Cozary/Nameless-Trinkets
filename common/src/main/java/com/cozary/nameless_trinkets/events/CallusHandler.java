package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.Callus;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class CallusHandler {

    public static float onPlayerHurt(Player player, DamageSource source, float originalAmount) {
        Callus.Stats config = Callus.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return originalAmount;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null)
            return originalAmount;

        var stack = accessories.getEquipped(ModItems.CALLUS.get());
        if (stack.isEmpty())
            return originalAmount;


        if (isNullifiedDamageType(source)) {
            return 0;
        } else if (source.is(DamageTypes.FALL)) {
            return originalAmount * (float) (1 - (config.fallDamageReductionPercentage / 100.0));
        } else {
            return originalAmount * (float) (1 - (config.generalDamageReductionPercentage / 100.0));
        }
    }

    private static boolean isNullifiedDamageType(DamageSource source) {
        return source.is(DamageTypes.CACTUS) ||
                source.is(DamageTypes.FALLING_ANVIL) ||
                source.is(DamageTypes.HOT_FLOOR) ||
                source.is(DamageTypes.SWEET_BERRY_BUSH);
    }


}
