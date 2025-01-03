package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.VampireBlood;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class VampireBloodEvents {

    @SubscribeEvent
    public static void function(LivingDeathEvent event) {
        VampireBlood.Stats config = VampireBlood.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getSource().getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.VAMPIRE_BLOOD.get());

            if (!stack.isEmpty() && !player.level().isClientSide) {
                ((ServerLevel) event.getEntity().getCommandSenderWorld()).sendParticles(ParticleTypes.SNEEZE, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 35, 1D, 1D, 1D, 0.1);
                player.heal(event.getEntity().getMaxHealth() * (config.healingPercentage / 100));
            }
        }
    }

}
