package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;

public class IceCubeEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (damageSource.getEntity() instanceof Player player) {
                IceCubeHandler.applySlowEffect(player, targetEntity);
            }
            return amount;
        });
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                if (IceCubeHandler.negateFreezeDamage(player, damageSource)) {
                    return 0.0f;
                }
            }
            return amount;
        });
    }
}
