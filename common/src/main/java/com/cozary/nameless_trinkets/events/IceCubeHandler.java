package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.IceCubeBase;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class IceCubeHandler {

    public static void applySlowEffect(LivingEntity entity) {
        IceCubeBase.Stats config = IceCubeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.SLOWNESS, config.slownessTime, config.slownessLevel);
        entity.addEffect(effectinstance);

    }

    public static boolean negateFreezeDamage(Player player, DamageSource damageSource) {
        IceCubeBase.Stats config = IceCubeBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return false;

        if (!player.isSpectator()) {


            if (config.inmuneToFreezing) {
                return damageSource.is(DamageTypeTags.IS_FREEZING);
            }

        }
        return false;
    }
}
