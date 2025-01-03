package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.VampireBlood;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class VampireBloodEvents {

    public static void register(){
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            VampireBlood.Stats config = VampireBlood.INSTANCE.getTrinketConfig();

            if (!config.isEnable)
                return;

            if (entity instanceof Player player && !player.isSpectator()) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.VAMPIRE_BLOOD.get());

                if (!stack.isEmpty() && !player.level().isClientSide) {
                    ((ServerLevel) killedEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.SNEEZE, killedEntity.getX(), killedEntity.getY(), killedEntity.getZ(), 35, 1D, 1D, 1D, 0.1);
                    player.heal(killedEntity.getMaxHealth() * (config.healingPercentage / 100));
                }
            }
        });
    }

}
