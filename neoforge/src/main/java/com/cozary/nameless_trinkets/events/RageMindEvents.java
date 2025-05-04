package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class RageMindEvents {

    @SubscribeEvent
    public static void getEntity(LivingIncomingDamageEvent event) {
        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }
            var stack = accessories.getEquipped(ModItems.RAGE_MIND.get());
            if (!stack.isEmpty()) {
                Entity entity = event.getSource().getEntity();

                if (entity instanceof LivingEntity) {

                    var entityType = entity.getType();
                    if (entityType != null) {
                        String entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
                        stack.getFirst().stack().set(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get(), entityKey);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void dealDamage(LivingIncomingDamageEvent event) {
        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        if (event.getSource().getEntity() instanceof Player player) {

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }
            var stack = accessories.getEquipped(ModItems.RAGE_MIND.get());
            if (!stack.isEmpty()) {

                if (stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

                    String entityString = stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());

                    ResourceLocation resourceLocation = ResourceLocation.parse(entityString);

                    EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);

                    Entity entity = entityType.create(player.level());

                    if (entity == null) {
                        return;
                    }

                    Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) entity.getClass();

                    if (event.getEntity() == null) {
                        return;
                    }

                    if (event.getEntity().getClass() == classEntity) {
                        event.setAmount(event.getOriginalAmount() * (config.damageMultiplierPercentage / 100));
                    }
                }


            }
        }
    }

}
