package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.GhastEye;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.Objects;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class GhastEyeEvents {

    @SubscribeEvent
    public static void obtainRegenOnKill(LivingDeathEvent event) {
        GhastEye.Stats config = GhastEye.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getSource().getEntity() instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.GHAST_EYE.get());

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
