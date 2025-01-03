package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SleepingPills;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.world.entity.player.Player;


public class SleepingPillsEvents {

    public static void register(){
        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> {
            SleepingPills.Stats config = SleepingPills.INSTANCE.getTrinketConfig();

            if (!config.isEnable)
                return null;

            if (!player.isSpectator()) {
                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SLEEPING_PILLS.get());

                if (!stack.isEmpty() && config.bedDisabled) {
                    return Player.BedSleepingProblem.OTHER_PROBLEM;
                }
            }
            return null;
        });
    }
    
}
