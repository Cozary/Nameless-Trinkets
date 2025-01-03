package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.SigilOfBaphomet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class SigilOfBaphometEvents {

    @SubscribeEvent
    public static void handleSigilKillCount(LivingDeathEvent event) {
        SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getSource().getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());

            if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) <= 10) {
                stack.getFirst().stack().set(ModDataComponents.SIGIL_COUNT.get(), stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) + 1);
            }
        }
    }

    @SubscribeEvent
    public static void grantSigilImmunityOnDamage(LivingDamageEvent.Pre event) {
        SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {
            if (event.getEntity() == player) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());

                if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0 && !player.level().isClientSide) {
                    ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY(), player.getZ(), 50, 0.5D, 1D, 0.5D, 0.1);
                    event.setNewDamage(0);
                }

            }
        }
    }

}
