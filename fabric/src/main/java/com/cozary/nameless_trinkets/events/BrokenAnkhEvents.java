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

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (entity instanceof Player player) {
                return !BrokenAnkhHandler.tryPreventDeath(player);
            }
            return true;
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {
                    BrokenAnkhHandler.restoreCooldownOnLogin(player);
                }
            });
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            server.execute(() -> {
                if (handler.player instanceof ServerPlayer player) {
                    BrokenAnkhHandler.saveCooldownOnLogout(player);
                }
            });
        });
    }
}
