package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.WoodenStickBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class WoodenStickHandler {

    public static boolean cancelWoodenStick(Player player, Item stack) {
        WoodenStickBase.Stats config = WoodenStickBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;


        if (!player.level().isClientSide()) {
            if (!player.getCooldowns().isOnCooldown(stack.getDefaultInstance())) {
                player.getCooldowns().addCooldown(stack.getDefaultInstance(), (int) config.cooldown);
                return true;
            }
        }
        return false;
    }
}
