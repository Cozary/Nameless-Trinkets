package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.PufferFishLiver;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PufferFishLiverEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
           if(damageSource.getEntity() instanceof Player player){
               PufferFishLiverHandler.applyPoisonEffect(player, targetEntity);
           }
            return amount;
        });
    }
}
