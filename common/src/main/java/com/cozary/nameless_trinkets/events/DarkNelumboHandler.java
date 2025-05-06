package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.DarkNelumboBase;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class DarkNelumboHandler {

    public static boolean blazeNucleusImmune(Player player, DamageSource source) {
        DarkNelumboBase.Stats config = DarkNelumboBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return false;

        if (player.isSpectator())
            return false;


        if (config.cancelLavaDamage) {
            return source.type().msgId().equals("lava");
        }

        return false;
    }
}
