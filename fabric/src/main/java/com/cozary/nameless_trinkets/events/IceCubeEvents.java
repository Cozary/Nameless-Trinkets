package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BlazeNucleus;
import com.cozary.nameless_trinkets.items.trinkets.FracturedNullstone;
import com.cozary.nameless_trinkets.items.trinkets.IceCube;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class IceCubeEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.ICE_CUBE.get());

                if (!stack.isEmpty()) {
                    MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, config.slownessTime, config.slownessLevel);
                    targetEntity.addEffect(effectinstance);
                }
            }
            return amount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.ICE_CUBE.get());

                if (!stack.isEmpty()) {
                    if (config.inmuneToFreezing) {
                        if (damageSource.is(DamageTypeTags.IS_FREEZING)) {
                            return 0;
                        }
                    }
                }
            }

            return amount;
        });
    }
}
