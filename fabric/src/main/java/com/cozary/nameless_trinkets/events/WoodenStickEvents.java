package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class WoodenStickEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            WoodenStick.Stats config = WoodenStick.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return damageAmount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {
                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return damageAmount;
                }
                var stack = accessories.getEquipped(ModItems.WOODEN_STICK.get());
                if (!stack.isEmpty() && !player.level().isClientSide) {
                    if (!player.getCooldowns().isOnCooldown(stack.getFirst().stack())) {
                        player.getCooldowns().addCooldown(stack.getFirst().stack(), (int) config.cooldown);
                        return 0;
                    }
                }
            }

            return damageAmount;
        });
    }
}
