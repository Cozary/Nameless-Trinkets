package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.GhastEye;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class GhastEyeEvents {

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            GhastEye.Stats config = GhastEye.INSTANCE.getTrinketConfig();

            if (!config.isEnable) return true;

            if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {
                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.GHAST_EYE.get());

                if (!stack.isEmpty()) {
                    if (!player.hasEffect(MobEffects.REGENERATION)) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, config.regenerationTime, config.regenerationLevel));
                    } else {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Objects.requireNonNull(player.getEffect(MobEffects.REGENERATION)).getDuration() + config.regenerationExtraTime, config.regenerationLevel));
                    }
                }
            }

            return true;
        });
    }

}
