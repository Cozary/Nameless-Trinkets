package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SleepingPills;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class SleepingPillsEvents {

    @SubscribeEvent
    public static void preventSleep(CanPlayerSleepEvent event) {

                event.setProblem(SleepingPillsHandler.preventSleep(event.getEntity()));

    }

}
