package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class EclipseAshesEvents {

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ECLIPSE_ASHES.get());
            if (!stack.isEmpty() && event.getSource().getEntity() instanceof LivingEntity attacker) {
                EclipseAshesHandler.onPlayerAttacked(player, attacker, stack);
            }
        }

        if (event.getSource().getEntity() instanceof Wolf wolf && EclipseAshesHandler.isPhantomWolf(wolf)) {
            EclipseAshesHandler.onPhantomWolfAttack(wolf, event.getEntity());
        }
    }
}
