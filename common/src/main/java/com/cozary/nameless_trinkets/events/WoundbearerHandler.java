package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.Woundbearer;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class WoundbearerHandler {

    public static void savePlayerDamageIncrement(Player player, float damageAmount) {
        Woundbearer.Stats config = Woundbearer.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }
        var stack = accessories.getEquipped(ModItems.WOUNDBEARER.get());
        if (!stack.isEmpty() && !player.level().isClientSide) {
            float damageIncrement = damageAmount * (config.damageConversionPercentage / 100);

            float currentDamage = stack.getFirst().stack().getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0f);

            float newDamage = currentDamage + damageIncrement;
            if (Float.isInfinite(newDamage) || newDamage > Float.MAX_VALUE) {
                newDamage = Float.MAX_VALUE;
            }

            stack.getFirst().stack().set(ModDataComponents.WOUNDBEARER_DAMAGE.get(), newDamage);
        }
    }
}
