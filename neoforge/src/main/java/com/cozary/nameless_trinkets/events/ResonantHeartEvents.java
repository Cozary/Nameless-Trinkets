package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ResonantHeart;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Random;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ResonantHeartEvents {

    @SubscribeEvent
    public static void heartSonicBoom(LivingDamageEvent.Post event) {
        ResonantHeart.Stats config = ResonantHeart.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();
        Random random = new Random();
        if (event.getEntity() instanceof Player player) {

            if (player.level().isClientSide)
                return;

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }

            var stack = accessories.getEquipped(ModItems.RESONANT_HEART.get());
            if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToActivateProbability) {

                if (src != null && !(src instanceof Player) && !player.level().isClientSide) {

                    launchSonicBoom(player, src, player.level());
                }
            }
        }
    }

    public static void launchSonicBoom(Player player, Entity src, Level level) {
        Vec3 origin = player.position().add(0.0, player.getEyeHeight() * 0.5, 0.0);
        Vec3 targetPos = src.position().add(0.0, src.getBbHeight() * 0.5, 0.0);
        Vec3 vecToTarget = targetPos.subtract(origin);
        Vec3 direction = vecToTarget.normalize();
        int particleCount = Mth.floor(vecToTarget.length()) + 7;

        ServerLevel serverLevel = (ServerLevel) level;

        for (int i = 1; i < particleCount; ++i) {
            Vec3 particlePos = origin.add(direction.scale(i));
            serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);
        }

        serverLevel.playSound(null, player.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, player.getSoundSource(), 3.0F, 1.0F);

        if (src instanceof LivingEntity target) {
            boolean damaged = target.hurtServer(serverLevel, serverLevel.damageSources().sonicBoom(player), 10.0F);
            if (damaged) {
                double knockbackResist = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double verticalKb = 0.5 * (1.0 - knockbackResist);
                double horizontalKb = 2.5 * (1.0 - knockbackResist);
                target.push(direction.x * horizontalKb, direction.y * verticalKb, direction.z * horizontalKb);
            }
        }

    }

}
