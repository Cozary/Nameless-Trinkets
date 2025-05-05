package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;

public class WoodenStickHandler {

    public static boolean cancelWoodenStick(Player player) {
        WoodenStick.Stats config = WoodenStick.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;

        var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return false;
            }
            var stack = accessories.getEquipped(ModItems.WOODEN_STICK.get());
            if (!stack.isEmpty() && !player.level().isClientSide) {
                if (!player.getCooldowns().isOnCooldown(stack.getFirst().stack().getItem())) {
                    player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), (int) config.cooldown);
                    return true;
                }
            }
        return false;
    }
}
