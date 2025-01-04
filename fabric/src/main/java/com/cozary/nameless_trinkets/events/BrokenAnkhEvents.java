package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh.getCooldown;
import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh.setCooldown;

public class BrokenAnkhEvents {

    private static final BrokenAnkh.Stats config = BrokenAnkh.INSTANCE.getTrinketConfig();

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (!config.isEnable) return true;

            if (entity instanceof Player player && !player.isSpectator()) {
                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BROKEN_ANKH.get());

                if (!stack.isEmpty() && player.isDeadOrDying() && !player.getCooldowns().isOnCooldown(stack.getFirst().stack()) && !player.level().isClientSide) {

                    ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
                    ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY(), player.getZ(), 50, 1D, 1D, 1D, 0.1);
                    ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);

                    player.setHealth(2.0F);
                    player.removeAllEffects();
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    player.getCooldowns().addCooldown(stack.getFirst().stack(), config.cooldown);

                    return false;
                }
            }

            return true;
        });


        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {
                    var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BROKEN_ANKH.get());

                    if (!stack.isEmpty()) {

                        player.getCooldowns().addCooldown(stack.getFirst().stack(), getCooldown(stack.getFirst().stack()));
                    }
                }
            });
        });


        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {
                    var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BROKEN_ANKH.get());

                    if (!stack.isEmpty()) {
                        setCooldown(stack.getFirst().stack(),
                                (int) (player.getCooldowns().getCooldownPercent(stack.getFirst().stack(), 0) * config.cooldown));
                    }
                }
            });
        });

    }
}
