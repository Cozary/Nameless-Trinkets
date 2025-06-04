package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;

public class IceCubeEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(damageSource.getEntity() instanceof Player player){

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ICE_CUBE.get());

                if (stack.isEmpty())
                    return amount;

                IceCubeHandler.applySlowEffect(player, targetEntity);
            }
            return amount;
        });
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(targetEntity instanceof Player player){

                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ICE_CUBE.get());

                if (stack.isEmpty())
                    return amount;

                if(IceCubeHandler.negateFreezeDamage(player, damageSource)){
                    return 0.0f;
                }
            }
            return amount;
        });
    }
}
