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
       float newFov = FOVHandler.onFOVUpdate(event.getPlayer(), event.getFovModifier());
       event.setNewFovModifier(newFov);
    }
}
