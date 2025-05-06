package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class FourLeafCloverEvents {

    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if ((event.getEntity() instanceof Player))
            return;

        if (event.getSource().getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.FOUR_LEAF_CLOVER.get());

            if (stack.isEmpty())
                return;

            FourLeafCloverHandler.entityKilled(player, event.getEntity());
        }
    }
}
