package com.cozary.nameless_trinkets.init;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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

    /**
     * Modifies the outgoing damage (player -> entity)
     */
    public interface DamageModifyCallback {
        Event<DamageModifyCallback> EVENT = EventFactory.createArrayBacked(DamageModifyCallback.class,
                (listeners) -> (target, source, amount) -> {
                    float modifiedAmount = amount;
                    for (DamageModifyCallback listener : listeners) {
                        modifiedAmount = listener.onDamage(target, source, modifiedAmount);
                    }
                    return modifiedAmount;
                });

        float onDamage(LivingEntity target, DamageSource source, float amount);
    }

    /**
     * Modifies mob experience drop
     */

    public interface ExperienceDropModifierCallback {
        Event<ExperienceDropModifierCallback> EVENT = EventFactory.createArrayBacked(ExperienceDropModifierCallback.class,
                (listeners) -> (entity, livingEntity) -> {
                    for (ExperienceDropModifierCallback listener : listeners) {
                        listener.onExperienceDrop(entity, livingEntity);
                    }
                });

        void onExperienceDrop(Entity entity, LivingEntity livingEntity);
    }


    /**
     * Cancels mob current target
     */
    public interface TargetingCallback {
        Event<TargetingCallback> EVENT = EventFactory.createArrayBacked(TargetingCallback.class,
                (listeners) -> (attacker, target) -> {
                    for (TargetingCallback listener : listeners) {
                        if (!listener.canTarget(attacker, target)) {
                            return false;
                        }
                    }
                    return true;
                });

        boolean canTarget(Mob attacker, LivingEntity target);
    }
}
