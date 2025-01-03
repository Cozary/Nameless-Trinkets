package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class FOVEvents {

    @SubscribeEvent
    public static void onFOVUpdate(ComputeFovModifierEvent event) {
        if (!ConfigurationHandler.GENERAL.disableFOV.get()) return;

        Player player = event.getPlayer();
        if (player == null) return;

        var stack0 = AccessoriesCapability.get(player).getEquipped(ModItems.CRACKED_CROWN.get());
        var stack1 = AccessoriesCapability.get(player).getEquipped(ModItems.GODS_CROWN.get());
        var stack2 = AccessoriesCapability.get(player).getEquipped(ModItems.SCARAB_AMULET.get());
        var stack3 = AccessoriesCapability.get(player).getEquipped(ModItems.SPEED_FORCE.get());

        if (!stack0.isEmpty() || !stack1.isEmpty() || !stack2.isEmpty() || !stack3.isEmpty()) {

            event.setNewFovModifier(1.0F);
        }
    }
}
