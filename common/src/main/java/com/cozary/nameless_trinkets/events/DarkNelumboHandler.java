package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.DarkNelumbo;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class DarkNelumboHandler {

    public static boolean blazeNucleusImmune(Player player, DamageSource source) {
        DarkNelumbo.Stats config = DarkNelumbo.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return false;

        if (player.isSpectator())
            return false;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return false;
        }
        var stack = accessories.getEquipped(ModItems.DARK_NELUMBO.get());
        if (!stack.isEmpty()) {
            if (config.cancelLavaDamage) {
                return source.type().msgId().equals("lava");
            }
        }
        return false;
    }
}
