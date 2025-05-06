package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.MoonStoneBase;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;

public class MoonStoneHandler {

    public static boolean moonStoneFallDamage(Player player, DamageSource damageSource) {
        MoonStoneBase.Stats config = MoonStoneBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;


        return damageSource.is(DamageTypes.FALL);

    }
}

