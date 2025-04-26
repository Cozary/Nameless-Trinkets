package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SigilOfBaphomet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;


public class SigilOfBaphometEvents {

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();

            if (!config.isEnable)
                return;

            if (entity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return;
                }
                var stack = accessories.getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());

                if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) <= config.invulnerabilityMaxTimeInTicks) {
                    stack.getFirst().stack().set(ModDataComponents.SIGIL_COUNT.get(), stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) + config.invulnerabilityAddTimeInTicks);
                }
            }
        });

        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, damageAmount) -> {
            SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return damageAmount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {
                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return damageAmount;
                }
                var stack = accessories.getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());

                if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0 && !player.level().isClientSide) {
                    ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY(), player.getZ(), 50, 0.5D, 1D, 0.5D, 0.1);
                    return 0;
                }
            }

            return damageAmount;
        });
    }

}
