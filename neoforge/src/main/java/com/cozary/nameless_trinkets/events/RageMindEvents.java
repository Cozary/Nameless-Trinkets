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

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class RageMindEvents {

    @SubscribeEvent
    public static void getEntity(LivingDamageEvent.Pre event) {
        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.RAGE_MIND.get());

            if (!stack.isEmpty()) {
                Entity entity = event.getSource().getEntity();

                if (entity instanceof LivingEntity) {

                    stack.getFirst().stack().set(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get(), BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                }
            }
        }
    }

    @SubscribeEvent
    public static void dealDamage(LivingDamageEvent.Pre event) {
        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        if (event.getSource().getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.RAGE_MIND.get());

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
                        event.setNewDamage(event.getOriginalDamage() * config.damageMultiplier);
                    }
                }


            }
        }
    }

}
