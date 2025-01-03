package com.cozary.nameless_trinkets.init;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class ModEvents {

    /**
     * Modify Base Block Break Speed
     */
    public interface BlockDestroySpeedCallback {
        Event<BlockDestroySpeedCallback> EVENT = EventFactory.createArrayBacked(BlockDestroySpeedCallback.class,
                (listeners) -> (player, state, baseSpeed) -> {
                    for (BlockDestroySpeedCallback listener : listeners) {
                        float result = listener.modifyDestroySpeed(player, state, baseSpeed);
                        if (result != baseSpeed) {
                            return result;
                        }
                    }
                    return baseSpeed;
                });

        float modifyDestroySpeed(Player player, BlockState state, float baseSpeed);
    }

}
