package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.FracturedNullstoneBase;
import net.minecraft.world.damagesource.DamageSource;

public class FracturedNullstoneHandler {

    public static float reduceMagicDamage(DamageSource damageSource, float originalDamage) {
        FracturedNullstoneBase.Stats config = FracturedNullstoneBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalDamage;


        //haha DamageTypeTag Magic doesn't exist

        if (damageSource.type().msgId().equals("indirectMagic") || damageSource.type().msgId().equals("magic")) {
            return originalDamage * (config.magicDamageReductionPercentage / 100);
        }

        return originalDamage;
    }
}

