package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class IceCubeEvents {

    @SubscribeEvent
    public static void applySlowEffect(LivingDamageEvent.Post event) {

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();

        if (src instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ICE_CUBE.get());

            if (stack.isEmpty())
                return;

            IceCubeHandler.applySlowEffect(player, (LivingEntity) event.getSource().getEntity());

        }
    }

    @SubscribeEvent
    public static void negateFreezeDamage(LivingIncomingDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ICE_CUBE.get());

            if (stack.isEmpty())
                return;

            event.setCanceled(IceCubeHandler.negateFreezeDamage(player, event.getSource()));

        }
    }


}
