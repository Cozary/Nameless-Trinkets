package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.items.trinkets.SigilOfBaphometBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class SigilOfBaphometHandler {

    public static void handleSigilKillCount(Item stack) {
        SigilOfBaphometBase.Stats config = SigilOfBaphometBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (stack.getDefaultInstance().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) <= 10) {
            stack.getDefaultInstance().set(ModDataComponents.SIGIL_COUNT.get(), stack.getDefaultInstance().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) + 1);
        }

    }

    public static boolean grantSigilImmunityOnDamage(Player player, Item stack) {
        SigilOfBaphometBase.Stats config = SigilOfBaphometBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;

        if (stack.getDefaultInstance().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0 && !player.level().isClientSide) {
            ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY(), player.getZ(), 50, 0.5D, 1D, 0.5D, 0.1);
            return true;
        }

        return false;
    }
}
