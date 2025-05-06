package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.CallusBase;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class CallusHandler {

    public static float onPlayerHurt(Player player, DamageSource source, float originalAmount) {
        CallusBase.Stats config = CallusBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
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
