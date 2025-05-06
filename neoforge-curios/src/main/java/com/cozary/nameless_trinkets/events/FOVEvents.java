package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class FOVEvents {

    @SubscribeEvent
    public static void onFOVUpdate(ComputeFovModifierEvent event) {

        //Todo add check stack

        float newFov = FOVHandler.onFOVUpdate(event.getPlayer(), event.getFovModifier());
        event.setNewFovModifier(newFov);
    }
}
