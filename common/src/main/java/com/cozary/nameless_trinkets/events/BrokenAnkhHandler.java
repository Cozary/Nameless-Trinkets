package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh.getCooldown;
import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh.setCooldown;

public class BrokenAnkhHandler {

    private static final BrokenAnkh.Stats config = BrokenAnkh.INSTANCE.getTrinketConfig();

    public static boolean tryPreventDeath(Player player) {
        if (!config.isEnable || player.isSpectator() || player.level().isClientSide) return false;

        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) return false;

        var stack = accessories.getEquipped(ModItems.BROKEN_ANKH.get());

        if (!stack.isEmpty()
                && player.isDeadOrDying()
                && !player.getCooldowns().isOnCooldown(stack.getFirst().stack().getItem())) {

            var level = (ServerLevel) player.getCommandSenderWorld();

            level.sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
            level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY(), player.getZ(), 50, 1D, 1D, 1D, 0.1);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);

            player.setHealth(2.0F);
            player.removeAllEffects();
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));

            player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), config.cooldown);

            return true;
        }

        return false;
    }

    public static void restoreCooldownOnLogin(ServerPlayer player) {
        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) return;

        var stack = accessories.getEquipped(ModItems.BROKEN_ANKH.get());
        if (!stack.isEmpty()) {
            player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), getCooldown(stack.getFirst().stack()));
        }
    }

    public static void saveCooldownOnLogout(ServerPlayer player) {
        if (!config.isEnable) return;

        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) return;

        var stack = accessories.getEquipped(ModItems.BROKEN_ANKH.get());
        if (!stack.isEmpty()) {
            setCooldown(stack.getFirst().stack(),
                    (int) (player.getCooldowns().getCooldownPercent(stack.getFirst().stack().getItem(), 0) * config.cooldown));
        }
    }
}