package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;

public class GravityAnchorEvents {

    public static void register() {
        // Hook into damage event to detect fall damage and trigger AOE slam
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                // Check if this is fall damage
                if (damageSource.is(DamageTypeTags.IS_FALL)) {
                    var stack = TrinketUtils.getEquippedTrinket(player, ModItems.GRAVITY_ANCHOR.get());
                    
                    if (!stack.isEmpty()) {
                        // Calculate fall distance from damage (fall damage = distance - 3)
                        float fallDistance = amount + 3;
                        
                        // Trigger the AOE slam effect
                        GravityAnchorHandler.onFallDamage(player, fallDistance, damageSource);
                    }
                }
            }
            return amount; // Don't modify the damage amount
        });
    }

}
