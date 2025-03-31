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
            RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return damageAmount;
            }

            if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return damageAmount;
                }
                var stack = accessories.getEquipped(ModItems.RAGE_MIND.get());

                if (!stack.isEmpty()) {

                    if (stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

                        String entityString = stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());

                        ResourceLocation resourceLocation = ResourceLocation.parse(entityString);

                        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);

                        Entity entity = entityType.create(player.level());

                        Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) entity.getClass();

                        if (targetEntity.getClass() == classEntity) {
                            return damageAmount * config.damageMultiplierPercentage;
                        }
                    }


                }
            }

            return damageAmount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.RAGE_MIND.get());

                if (!stack.isEmpty()) {

                    Entity sourceEntity = damageSource.getEntity();
                    if (sourceEntity != null) {
                        var entityType = sourceEntity.getType();
                        if (entityType != null) {
                            String entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
                            stack.getFirst().stack().set(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get(), entityKey);
                        }
                    }
                }
            }

            return amount;
        });
    }
}
