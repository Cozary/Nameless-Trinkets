package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.IceCube;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class IceCubeHandler {

    public static void applySlowEffect(Player player, LivingEntity entity) {
        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }
            var stack = accessories.getEquipped(ModItems.ICE_CUBE.get());
            if (!stack.isEmpty()) {
                MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, config.slownessTime, config.slownessLevel);
                entity.addEffect(effectinstance);
            }
        }

    public static boolean negateFreezeDamage(Player player, DamageSource damageSource) {
        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return false;

        if (!player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return false;
                }
                var stack = accessories.getEquipped(ModItems.ICE_CUBE.get());
                if (!stack.isEmpty()) {
                    if (config.inmuneToFreezing) {
                        return damageSource.is(DamageTypeTags.IS_FREEZING);
                    }
                }
        }
        return false;
    }
}
