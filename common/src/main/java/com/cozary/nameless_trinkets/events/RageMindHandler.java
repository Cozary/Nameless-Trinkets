package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.RageMind;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RageMindHandler {

    public static void getEntity(Player player, LivingEntity livingEntity) {
        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }

        if (livingEntity == null)
            return;

        var stack = accessories.getEquipped(ModItems.RAGE_MIND.get());
        if (!stack.isEmpty()) {

            var entityType = livingEntity.getType();
            String entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
            stack.getFirst().stack().set(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get(), entityKey);
        }
    }

    public static float dealDamage(Player player, Entity targetEntity, float originalAmount) {
        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return originalAmount;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return originalAmount;
        }
        var stack = accessories.getEquipped(ModItems.RAGE_MIND.get());
        if (!stack.isEmpty()) {

            if (stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

                String entityString = stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());

                ResourceLocation resourceLocation = ResourceLocation.parse(entityString);

                EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);

                Entity entity = entityType.create(player.level());

                if (entity == null) {
                    return originalAmount;
                }

                Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) entity.getClass();

                if (targetEntity == null) {
                    return originalAmount;
                }

                if (targetEntity.getClass() == classEntity) {
                    return originalAmount * (config.damageMultiplierPercentage / 100);
                }
            }


        }
        return originalAmount;
    }
}
