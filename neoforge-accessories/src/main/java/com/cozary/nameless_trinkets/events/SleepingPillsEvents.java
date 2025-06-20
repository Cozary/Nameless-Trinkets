package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class SleepingPillsEvents {

    @SubscribeEvent
    public static void preventSleep(CanPlayerSleepEvent event) {

        var stack = TrinketUtils.getEquippedTrinket(event.getEntity(), ModItems.SLEEPING_PILLS.get());

        if (stack.isEmpty())
            return;

        event.setProblem(SleepingPillsHandler.preventSleep(event.getEntity()));

    }

}
