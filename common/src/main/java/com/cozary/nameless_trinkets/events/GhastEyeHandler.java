package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.GhastEye;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class GhastEyeHandler {

    public static void obtainRegenOnKill(Player player) {
        GhastEye.Stats config = GhastEye.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!player.isSpectator()) {

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }
            var stack = accessories.getEquipped(ModItems.GHAST_EYE.get());
            if (!stack.isEmpty()) {
                if (!player.hasEffect(MobEffects.REGENERATION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, config.regenerationTime, config.regenerationLevel));
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Objects.requireNonNull(player.getEffect(MobEffects.REGENERATION)).getDuration() + config.regenerationExtraTime, config.regenerationLevel));
                }
            }
        }
    }

}
