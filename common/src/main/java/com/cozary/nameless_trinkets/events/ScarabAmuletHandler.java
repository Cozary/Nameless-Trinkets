package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.ScarabAmuletBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.player.Player;

public class ScarabAmuletHandler {

    public static boolean shouldPreventHuskTargeting(Player player, Entity attacker) {
        ScarabAmuletBase.Stats config = ScarabAmuletBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) return false;
        if (player.level().isClientSide()) return false;


        return attacker instanceof Husk;
    }
}
