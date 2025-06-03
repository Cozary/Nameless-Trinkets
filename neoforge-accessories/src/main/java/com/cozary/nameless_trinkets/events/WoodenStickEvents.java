package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class WoodenStickEvents {

    @SubscribeEvent
    public static void cancelWoodenStick(LivingIncomingDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.WOODEN_STICK.get());

            if (stack.isEmpty())
                return;

            event.setCanceled(WoodenStickHandler.cancelWoodenStick(player, stack.getFirst().stack().getItem()));
        }
    }
}
