package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.ResonantHeartBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ResonantHeartHandler {

    public static void heartSonicBoom(Player player, DamageSource damageSource) {
        ResonantHeartBase.Stats config = ResonantHeartBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Entity src = damageSource.getEntity();
        Random random = new Random();

        if (player.level().isClientSide)
            return;

        if (random.nextInt(100) <= config.chanceToActivateProbability) {

            if (src != null && !(src instanceof Player) && !player.level().isClientSide) {

                launchSonicBoom(player, src, player.level());
            }
        }
    }

    public static void launchSonicBoom(Player player, Entity src, Level level) {
        ResonantHeartBase.Stats config = ResonantHeartBase.INSTANCE.getTrinketConfig();

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
            boolean damaged = target.hurtServer(serverLevel ,serverLevel.damageSources().sonicBoom(player), config.sonicBoomDamage);
                if (damaged) {
                double knockbackResist = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double verticalKb = 0.5 * (1.0 - knockbackResist);
                double horizontalKb = 2.5 * (1.0 - knockbackResist);
                target.push(direction.x * horizontalKb, direction.y * verticalKb, direction.z * horizontalKb);
            }
        }

    }

}
