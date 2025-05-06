package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.items.trinkets.WoundbearerBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class WoundbearerHandler {

    public static void savePlayerDamageIncrement(Player player, float damageAmount, Item stack) {
        WoundbearerBase.Stats config = WoundbearerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!player.level().isClientSide) {
            float damageIncrement = damageAmount * (config.damageConversionPercentage / 100);

            float currentDamage = stack.getDefaultInstance().getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0f);

            float newDamage = currentDamage + damageIncrement;
            if (Float.isInfinite(newDamage) || newDamage > Float.MAX_VALUE) {
                newDamage = Float.MAX_VALUE;
            }

            stack.getDefaultInstance().set(ModDataComponents.WOUNDBEARER_DAMAGE.get(), newDamage);
        }
    }
}
