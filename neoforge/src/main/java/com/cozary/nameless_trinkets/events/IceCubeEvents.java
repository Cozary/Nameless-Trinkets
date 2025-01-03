package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static net.neoforged.neoforge.event.EventHooks.onBlockPlace;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class IceCubeEvents {

    @SubscribeEvent
    public static void applySlowEffect(LivingDamageEvent.Post event) {
        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();

        if (src instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ICE_CUBE.get());

            if (!stack.isEmpty()) {
                MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, config.slownessTime, config.slownessLevel);
                LivingEntity potionGo = event.getEntity();
                potionGo.addEffect(effectinstance);
            }
        }
    }

    @SubscribeEvent
    public static void placeFrostedIce(PlayerTickEvent.Pre event) {
        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        Player player = event.getEntity();
        BlockPos pos = player.blockPosition();
        Level world = player.level();

        if (player instanceof ServerPlayer && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ICE_CUBE.get());

            if (!stack.isEmpty() && !player.level().isClientSide) {

                BlockState block = Blocks.ICE.defaultBlockState();
                ((ServerLevel) player.getCommandSenderWorld()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, block), player.getX(), player.getY(), player.getZ(), 1, 0.5D, 1D, 0.5D, 0.1);

                if (player.onGround()) {

                    BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
                    float f = (float) Math.min(16, 2 + config.frostWalkerLevel);
                    BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

                    for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset((int) -f, (int) -1.0D, (int) -f), pos.offset((int) f, (int) -1.0D, (int) f))) {
                        if (blockpos.closerToCenterThan(player.position(), f)) {

                            blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                            BlockState blockstate1 = world.getBlockState(blockpos$mutable);

                            if (blockstate1.isAir()) {

                                BlockState blockstate2 = world.getBlockState(blockpos);
                                boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(LiquidBlock.LEVEL) == 0;

                                if (blockstate2 == FrostedIceBlock.meltsInto() && isFull && blockstate.canSurvive(world, blockpos) && world.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !onBlockPlace(player, BlockSnapshot.create(world.dimension(), world, blockpos), Direction.UP)) {

                                    world.setBlockAndUpdate(blockpos, blockstate);
                                    world.scheduleTick(blockpos, Blocks.FROSTED_ICE, Mth.nextInt(player.getRandom(), 60, 120));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void negateFreezeDamage(LivingDamageEvent.Pre event) {
        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getEntity() instanceof Player player && !player.isSpectator()) {
            if (event.getEntity() == player) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ICE_CUBE.get());

                if (!stack.isEmpty()) {
                    if (config.inmuneToFreezing) {
                        if (event.getSource().is(DamageTypeTags.IS_FREEZING)) {
                            event.setNewDamage(0);
                        }
                    }
                }
            }
        }
    }
}
