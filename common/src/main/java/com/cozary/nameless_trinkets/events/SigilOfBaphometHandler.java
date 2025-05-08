package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SigilOfBaphomet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class SigilOfBaphometHandler {

    public static void handleSigilKillCount(Player player) {
        SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }
        var stack = accessories.getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());
        if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) <= 10) {
            stack.getFirst().stack().set(ModDataComponents.SIGIL_COUNT.get(), stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) + 1);
        }

    }

    public static boolean grantSigilImmunityOnDamage(Player player) {
        SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return false;
        }
        var stack = accessories.getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());
        if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0 && !player.level().isClientSide) {
            ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY(), player.getZ(), 50, 0.5D, 1D, 0.5D, 0.1);
            return true;
        }

        return false;
    }
}
