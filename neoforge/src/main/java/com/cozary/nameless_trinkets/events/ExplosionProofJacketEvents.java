package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ExplosionProofJacketEvents {

    @SubscribeEvent
    public static void handleExplosionDamageReduction(LivingDamageEvent.Pre event) {
        if (event.getOriginalDamage() > 0 && event.getEntity() instanceof Player player) {
            float newDamage = ExplosionProofJacketHandler.handleExplosionDamageReduction(player, event.getSource(), event.getOriginalDamage());
            event.setNewDamage(newDamage);
        }
    }

}
