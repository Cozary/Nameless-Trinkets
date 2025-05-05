package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SigilOfBaphomet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class SigilOfBaphometEvents {

    @SubscribeEvent
    public static void handleSigilKillCount(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof Player player) {
            SigilOfBaphometHandler.handleSigilKillCount(player);
        }
    }

    @SubscribeEvent
    public static void grantSigilImmunityOnDamage(LivingIncomingDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

            event.setCanceled(SigilOfBaphometHandler.grantSigilImmunityOnDamage(player));

        }



    }

}
