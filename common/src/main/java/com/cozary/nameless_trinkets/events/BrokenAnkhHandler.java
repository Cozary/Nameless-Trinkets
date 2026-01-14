package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.BrokenAnkhBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkhBase.getCooldown;
import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkhBase.setCooldown;

public class BrokenAnkhHandler {

    private static final BrokenAnkhBase.Stats config = BrokenAnkhBase.INSTANCE.getTrinketConfig();

    public static boolean tryPreventDeath(Player player, Item stack) {
        if (!config.isEnable || player.isSpectator() || player.level().isClientSide) return false;


        if (player.isDeadOrDying()
                && !player.getCooldowns().isOnCooldown(stack.getDefaultInstance())) {

            var level = (ServerLevel) player.level();

            level.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
            level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY(), player.getZ(), 50, 1D, 1D, 1D, 0.1);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);

            player.setHealth(2.0F);
            player.removeAllEffects();
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));

            player.getCooldowns().addCooldown(stack.getDefaultInstance(), config.cooldown);

            return true;
        }

        return false;
    }

    public static void restoreCooldownOnLogin(ServerPlayer player, Item stack) {

        player.getCooldowns().addCooldown(stack.getDefaultInstance(), getCooldown(stack.getDefaultInstance()));

    }

    public static void saveCooldownOnLogout(ServerPlayer player, Item stack) {
        if (!config.isEnable) return;


        setCooldown(stack.getDefaultInstance(),
                (int) (player.getCooldowns().getCooldownPercent(stack.getDefaultInstance(), 0) * config.cooldown));

    }
}