package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.AmphibiousHands;
import com.cozary.nameless_trinkets.items.trinkets.BlazeNucleus;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BlazeNucleusEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            BlazeNucleus.Stats config = BlazeNucleus.INSTANCE.getTrinketConfig();

            if (!config.isEnable) return amount;
            Entity sourceEntity = damageSource.getEntity();

            if (sourceEntity instanceof Player attacker) {

                var accessories = AccessoriesCapability.get(attacker);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.BLAZE_NUCLEUS.get());

                if (!stack.isEmpty()) {
                    targetEntity.setRemainingFireTicks(config.setEnemyInFireTicks);
                    attacker.clearFire();
                }
            }
            return amount;
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            BlazeNucleus.Stats config = BlazeNucleus.INSTANCE.getTrinketConfig();
            if (!config.isEnable) return amount;


            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.BLAZE_NUCLEUS.get());

                if (!stack.isEmpty()) {
                    if (config.fireDamageReductionPercentage < 100) {
                        if (damageSource.is(DamageTypes.LAVA) ||
                                damageSource.is(DamageTypes.ON_FIRE) ||
                                damageSource.is(DamageTypes.IN_FIRE) ||
                                damageSource.is(DamageTypes.HOT_FLOOR)) {

                            amount *= (float) (1 - (config.fireDamageReductionPercentage / 100.0));
                        }
                    }
                }
            }
            return amount;
        });
    }

}
