package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.TrueHeartOfTheSea;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class TrueHeartOfTheSeaEvents {

    @SubscribeEvent
    public static void function(PlayerEvent.BreakSpeed event) {

        var stack = TrinketUtils.getEquippedTrinket(event.getEntity(), ModItems.TRUE_HEART_OF_THE_SEA.get());

        if (stack.isEmpty())
            return;

        float newSpeed = TrueHeartOfTheSeaHandler.function(event.getEntity(), event.getOriginalSpeed());
                event.setNewSpeed(newSpeed);


    }
}
