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
        SleepingPills.Stats config = SleepingPills.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Player player = event.getEntity();

        if (!player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SLEEPING_PILLS.get());

            if (!stack.isEmpty() && config.bedDisabled) {
                event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
            }
        }
    }

}
