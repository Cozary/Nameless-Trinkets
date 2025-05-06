package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.GhastEyeBase;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class GhastEyeHandler {

    public static void obtainRegenOnKill(Player player) {
        GhastEyeBase.Stats config = GhastEyeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!player.isSpectator()) {


            if (!player.hasEffect(MobEffects.REGENERATION)) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, config.regenerationTime, config.regenerationLevel));
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Objects.requireNonNull(player.getEffect(MobEffects.REGENERATION)).getDuration() + config.regenerationExtraTime, config.regenerationLevel));
            }

        }
    }

}
