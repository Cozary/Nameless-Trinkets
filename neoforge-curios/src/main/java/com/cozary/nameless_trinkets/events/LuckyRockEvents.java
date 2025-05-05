package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LuckyRockEvents {

    @SubscribeEvent
    public static void function(BlockEvent.BreakEvent event) {

        LuckyRockHandler.function(event.getPlayer(), event.getState(), event.getPos());

    }
}
