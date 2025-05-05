package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.IceCube;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class IceCubeEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(damageSource.getEntity() instanceof Player player){
                IceCubeHandler.applySlowEffect(player, targetEntity);
            }
            return amount;
        });
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(targetEntity instanceof Player player){
                if(IceCubeHandler.negateFreezeDamage(player, damageSource)){
                    return 0.0f;
                }
            }
            return amount;
        });
    }
}
