package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ResonantHeartEvents {

    @SubscribeEvent
    public static void heartSonicBoom(LivingDamageEvent.Post event) {

        if (event.getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.RESONANT_HEART.get());

            if (stack.isEmpty())
                return;

            ResonantHeartHandler.heartSonicBoom(player, event.getSource());
        }
    }

}
