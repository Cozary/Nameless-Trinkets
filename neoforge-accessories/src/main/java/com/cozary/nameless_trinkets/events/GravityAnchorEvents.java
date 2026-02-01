package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class GravityAnchorEvents {

    @SubscribeEvent
    public static void onFallDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        DamageSource damageSource = event.getSource();
        
        // Check if this is fall damage
        if (!damageSource.is(DamageTypeTags.IS_FALL))
            return;

        var stack = TrinketUtils.getEquippedTrinket(player, ModItems.GRAVITY_ANCHOR.get());

        if (stack.isEmpty())
            return;

        // Calculate fall distance from damage (fall damage = distance - 3)
        float fallDistance = event.getNewDamage() + 3;

        // Trigger the AOE slam effect
        GravityAnchorHandler.onFallDamage(player, fallDistance, damageSource);
    }
}
