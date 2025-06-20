package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class GhastEyeEvents {

    @SubscribeEvent
    public static void obtainRegenOnKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.GHAST_EYE.get());

            if (stack.isEmpty())
                return;

            GhastEyeHandler.obtainRegenOnKill(player);
        }
    }

}
