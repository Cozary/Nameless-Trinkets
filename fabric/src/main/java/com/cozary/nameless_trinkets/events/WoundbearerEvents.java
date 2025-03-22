package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.Woundbearer;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class WoundbearerEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            Woundbearer.Stats config = Woundbearer.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return damageAmount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {
                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return damageAmount;
                }
                var stack = accessories.getEquipped(ModItems.WOUNDBEARER.get());

                if (!stack.isEmpty() && !player.level().isClientSide) {
                    float damageIncrement = damageAmount * (config.damageConversionPercentage / 100);

                    stack.getFirst().stack().set(ModDataComponents.WOUNDBEARER_DAMAGE.get(), stack.getFirst().stack().getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue() + damageIncrement);

                }
            }

            return damageAmount;
        });
    }
}
