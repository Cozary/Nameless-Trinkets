package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.IceCube;
import com.cozary.nameless_trinkets.items.trinkets.PufferFishLiver;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PufferFishLiverEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            PufferFishLiver.Stats config = PufferFishLiver.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {

                Random random = new Random();
                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.PUFFER_FISH_LIVER.get());
                if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToApplyPoison) {
                    MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.POISON, config.poisonTime, config.poisonLevel);
                    targetEntity.addEffect(effectinstance);
                }
            }
            return amount;
        });
    }
}
