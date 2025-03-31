package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FracturedNullstone;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class FracturedNullstoneEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            FracturedNullstone.Stats config = FracturedNullstone.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.FRACTURED_NULLSTONE.get());

                if (!stack.isEmpty()) {
                    //haha DamageTypeTag Magic doesn't exist

                    if (damageSource.type().msgId().equals("indirectMagic") || damageSource.type().msgId().equals("magic")) {
                        return amount * (config.magicDamageReductionPercentage / 100);
                    }
                }
            }

            return amount;
        });
    }
}
