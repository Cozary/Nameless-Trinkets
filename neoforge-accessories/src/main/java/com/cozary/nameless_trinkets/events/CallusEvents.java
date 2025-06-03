package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.Callus;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class CallusEvents {

    @SubscribeEvent
    public static void applyCallusDamageReduction(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        var stack = TrinketUtils.getEquippedTrinket(player, ModItems.CALLUS.get());

        if (stack.isEmpty())
            return;

        DamageSource damageSource = event.getSource();
        float originalDamage = event.getNewDamage();
        float newDamage = CallusHandler.onPlayerHurt(player, damageSource, originalDamage);

        event.setNewDamage(newDamage);
    }
}
