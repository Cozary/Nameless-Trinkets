package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.Callus;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class CallusEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            Callus.Stats config = Callus.INSTANCE.getTrinketConfig();

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.CALLUS.get());

                if (!stack.isEmpty()) {
                    if (isNullifiedDamageType(damageSource)) {
                        return 0.0f;
                    }

                    if (damageSource.is(DamageTypes.FALL)) {
                        amount *= (float) (1 - (config.fallDamageReductionPercentage / 100.0));
                    } else {
                        amount *= (float) (1 - (config.generalDamageReductionPercentage / 100.0));
                    }

                    if (amount <= 0.0f) {
                        return 0.0f;
                    }
                }
            }

            return amount;
        });
    }

    private static boolean isNullifiedDamageType(DamageSource source) {
        return source.is(DamageTypes.CACTUS) ||
                source.is(DamageTypes.FALLING_ANVIL) ||
                source.is(DamageTypes.HOT_FLOOR) ||
                source.is(DamageTypes.SWEET_BERRY_BUSH);
    }

}
