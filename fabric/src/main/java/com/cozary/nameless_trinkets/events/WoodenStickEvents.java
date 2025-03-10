package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ReverseCard;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

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
                    if (!player.getCooldowns().isOnCooldown(stack.getFirst().stack().getItem())) {
                        player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), (int) config.cooldown);
                        return 0;
                    }
                }
            }

            return damageAmount;
        });
    }
}
