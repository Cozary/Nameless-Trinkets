package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LuckyRockEvents {

    @SubscribeEvent
    public static void function(BreakBlockEvent event) {

        var stack = TrinketUtils.getEquippedTrinket(event.getPlayer(), ModItems.LUCKY_ROCK.get());

        if (stack.isEmpty())
            return;

        LuckyRockHandler.function(event.getPlayer(), event.getState(), event.getPos());

    }
}
