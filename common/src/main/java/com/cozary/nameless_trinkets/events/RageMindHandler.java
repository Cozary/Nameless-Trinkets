package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.items.trinkets.RageMindBase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RageMindHandler {

    public static void getEntity(LivingEntity livingEntity, ItemStack stack) {
        RageMindBase.Stats config = RageMindBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (livingEntity == null)
            return;

        var entityType = livingEntity.getType();
        String entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
        stack.set(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get(), entityKey);

    }

    public static float dealDamage(Player player, Entity targetEntity, float originalAmount, ItemStack stack) {
        RageMindBase.Stats config = RageMindBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return originalAmount;


        if (stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

            String entityString = stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());

            Identifier resourceLocation = Identifier.parse(entityString);

            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation).get().value();

            Entity entity = entityType.create(player.level(), EntitySpawnReason.SPAWN_ITEM_USE);

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


        return originalAmount;
    }
}
