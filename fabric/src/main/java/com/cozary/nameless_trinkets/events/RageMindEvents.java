package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.RageMind;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RageMindEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
           if(damageSource.getEntity() instanceof Player player){
              return RageMindHandler.dealDamage(player, targetEntity, damageAmount);
           }
            return damageAmount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if(damageSource.getEntity() instanceof Player player){
                RageMindHandler.getEntity(player, targetEntity);
            }
            return amount;
        });
    }
}
