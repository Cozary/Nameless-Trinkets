package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LunarCrestEvents {

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        DamageSource damageSource = event.getSource();
        if (damageSource.getEntity() instanceof Player player) {
            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.LUNAR_CREST.get());
            if (!stack.isEmpty()) {
                float newDamage = LunarCrestHandler.dealDamage(event.getEntity(), damageSource, event.getAmount(), player, stack);
                event.setAmount(newDamage);
            }
        } else if (damageSource.getEntity() instanceof Wolf wolf && wolf.isTame() && wolf.getOwner() instanceof Player owner) {
            var stack = TrinketUtils.getEquippedTrinket(owner, ModItems.LUNAR_CREST.get());
            if (!stack.isEmpty()) {
                float newDamage = LunarCrestHandler.dealWolfDamage(event.getEntity(), damageSource, event.getAmount(), wolf, owner, stack);
                event.setAmount(newDamage);
            }
        }
    }
}
