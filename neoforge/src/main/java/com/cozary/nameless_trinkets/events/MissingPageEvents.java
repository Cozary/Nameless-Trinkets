package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.MissingPage;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class MissingPageEvents {

    public static boolean isValidTarget(LivingEntity ent) {
        return (ent.getType() != EntityType.PLAYER) && (!ent.isInvulnerable());
    }

    @SubscribeEvent
    public static void triggerDamageReflection(LivingDamageEvent.Post event) {
        MissingPage.Stats config = MissingPage.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();
        Random random = new Random();
        if (event.getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.MISSING_PAGE.get());

            if (!stack.isEmpty() && random.nextInt(100) <= config.activationPercentage) {

                AABB targetBox = new AABB(player.position(), player.position()).inflate(config.radiusInBlocks);

                List<LivingEntity> foundTarget =
                        event.getEntity().level().getEntitiesOfClass(LivingEntity.class, targetBox, MissingPageEvents::isValidTarget);

                if (src != null && !(src instanceof Player) && !foundTarget.isEmpty() && !player.level().isClientSide) {

                    for (LivingEntity livingEntity : foundTarget) {
                        ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.SOUL, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 35, 1D, 1D, 1D, 0.1);
                        livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.percentageOfDamage / 100));
                    }
                }
            }
        }
    }

}
