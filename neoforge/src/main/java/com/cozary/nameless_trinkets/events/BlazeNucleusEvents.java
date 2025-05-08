package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class BlazeNucleusEvents {

    @SubscribeEvent
    public static void setFireEntity(LivingDamageEvent.Pre event) {
        Entity src = event.getSource().getEntity();
        Entity target = event.getEntity();
        float modified = BlazeNucleusHandler.onAttackerHit(src, target, event.getOriginalDamage());
        event.setNewDamage(modified);
    }

    @SubscribeEvent
    public static void blazeNucleusImmune(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            float modified = BlazeNucleusHandler.onPlayerHurt(player, event.getSource(), event.getOriginalDamage());
            event.setNewDamage(modified);
        }
    }
}
