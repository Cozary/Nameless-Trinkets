package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.BlazeNucleusBase;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BlazeNucleusHandler {

    public static float onAttackerHit(Entity attacker, Entity target, float originalAmount) {
        BlazeNucleusBase.Stats config = BlazeNucleusBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable || !(attacker instanceof Player player)) return originalAmount;


        if (target instanceof LivingEntity livingTarget) {
            livingTarget.setRemainingFireTicks(config.setEnemyInFireTicks);
        }
        player.clearFire();


        return originalAmount;
    }

    public static float onPlayerHurt(Player player, DamageSource source, float originalAmount) {
        BlazeNucleusBase.Stats config = BlazeNucleusBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable || player.isSpectator()) return originalAmount;


        if (config.fireDamageReductionPercentage < 100) {
            boolean isFireDamage = source.is(DamageTypeTags.IS_FIRE) ||
                    source.is(DamageTypes.LAVA) ||
                    source.is(DamageTypes.ON_FIRE) ||
                    source.is(DamageTypes.IN_FIRE) ||
                    source.is(DamageTypes.HOT_FLOOR);

            if (isFireDamage) {
                return originalAmount * (1 - (config.fireDamageReductionPercentage / 100f));
            }
        }

        return originalAmount;
    }
}
