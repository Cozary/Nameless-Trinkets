package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class WoundbearerEvents {

    @SubscribeEvent
    public static void savePlayerDamageIncrement(LivingIncomingDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.WOUNDBEARER.get());

            if (stack.isEmpty())
                return;

            WoundbearerHandler.savePlayerDamageIncrement(player, event.getAmount(), stack.getFirst().stack().getItem());
        }
    }
}
