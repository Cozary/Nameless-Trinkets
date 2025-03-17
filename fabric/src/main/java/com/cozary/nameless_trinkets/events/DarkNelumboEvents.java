package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.DarkNelumbo;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class DarkNelumboEvents {

    public static void register(){
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            DarkNelumbo.Stats config = DarkNelumbo.INSTANCE.getTrinketConfig();
            if (!config.isEnable) return amount;

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }

                var stack = accessories.getEquipped(ModItems.DARK_NELUMBO.get());

                if (!stack.isEmpty()) {
                    if (config.cancelLavaDamage) {
                        if (damageSource.is(DamageTypes.LAVA)) {

                            amount = 0;
                        }
                    }
                }
            }
            return amount;
        });
    }
}
