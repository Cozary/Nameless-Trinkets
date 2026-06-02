package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class RageMindEvents {

    @SubscribeEvent
    public static void getEntity(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.RAGE_MIND.get());

            if (stack.isEmpty())
                return;

            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                RageMindHandler.getEntity(attacker, stack);
            }
        }
    }

    @SubscribeEvent
    public static void dealDamage(LivingIncomingDamageEvent event) {

        if (event.getSource().getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.RAGE_MIND.get());

            if (stack.isEmpty())
                return;

            float newDamage = RageMindHandler.dealDamage(player, event.getEntity(), event.getAmount(), stack);
            event.setAmount(newDamage);
        }
    }

}