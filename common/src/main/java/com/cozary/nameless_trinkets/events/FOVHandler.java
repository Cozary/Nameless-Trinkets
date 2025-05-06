package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import net.minecraft.world.entity.player.Player;

public class FOVHandler {

    public static float onFOVUpdate(Player player, float originalFov) {
        if (!CommonConfigManager.getConfig().isDisableFOV()) return originalFov;

        //todo

        /*var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return originalFov;
        }

        var stack0 = accessories.getEquipped(ModItems.CRACKED_CROWN.get());
        var stack1 = accessories.getEquipped(ModItems.GODS_CROWN.get());
        var stack2 = accessories.getEquipped(ModItems.SCARAB_AMULET.get());
        var stack3 = accessories.getEquipped(ModItems.SPEED_FORCE.get());

        if (!stack0.isEmpty() || !stack1.isEmpty() || !stack2.isEmpty() || !stack3.isEmpty()) {
            return 1.0f;
        }*/
        return originalFov;
    }
}
