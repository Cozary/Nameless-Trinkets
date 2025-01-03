package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BlazeNucleus;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class BlazeNucleusEvents {

    @SubscribeEvent
    public static void setFireEntity(LivingDamageEvent.Pre event) {
        BlazeNucleus.Stats config = BlazeNucleus.INSTANCE.getTrinketConfig();
        DamageSource source = event.getSource();
        Entity src = source.getEntity();

        if (!config.isEnable)
            return;

        if (!(src instanceof Player attacker))
            return;

        var stack = AccessoriesCapability.get(attacker).getEquipped(ModItems.BLAZE_NUCLEUS.get());

        if (!stack.isEmpty()) {
            Entity target = event.getEntity();

            if (target instanceof LivingEntity livingTarget) {
                livingTarget.setRemainingFireTicks(config.setEnemyInFireTicks);
            }

            attacker.clearFire();
        }
    }

    @SubscribeEvent
    public static void blazeNucleusImmune(LivingDamageEvent.Pre event) {
        BlazeNucleus.Stats config = BlazeNucleus.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(event.getEntity() instanceof Player player) || player.isSpectator())
            return;

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BLAZE_NUCLEUS.get());

        if (!stack.isEmpty()) {
            if (config.fireDamageReductionPercentage < 100) {
                if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
                    event.setNewDamage(event.getOriginalDamage() * (1 - (config.fireDamageReductionPercentage / 100)));
                }
            }
        }
    }

}