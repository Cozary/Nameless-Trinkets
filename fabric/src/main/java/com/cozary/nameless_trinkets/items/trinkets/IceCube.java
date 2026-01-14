package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class IceCube extends IceCubeBase implements Accessory {

    public IceCube() {
        super();
        AccessoryRegistry.register(this, this);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) {
            return;
        }

        Stats config = IceCubeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            return;
        }

        if (!(player instanceof ServerPlayer) || player.isSpectator() || player.level().isClientSide()) {
            return;
        }

        Level world = player.level();
        BlockPos pos = player.blockPosition();

        if (player.onGround()) {
            BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
            float radius = (float) Math.min(16, 2 + config.frostWalkerLevel);
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset((int) -radius, -1, (int) -radius), pos.offset((int) radius, -1, (int) radius))) {
                if (blockPos.closerToCenterThan(player.position(), radius)) {
                    mutablePos.set(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
                    BlockState aboveBlockState = world.getBlockState(mutablePos);

                    if (aboveBlockState.isAir()) {
                        BlockState belowBlockState = world.getBlockState(blockPos);
                        boolean isFullWaterBlock = belowBlockState.getBlock() == Blocks.WATER && belowBlockState.getValue(LiquidBlock.LEVEL) == 0;

                        if (belowBlockState == FrostedIceBlock.meltsInto() && isFullWaterBlock && blockstate.canSurvive(world, blockPos) && world.isUnobstructed(blockstate, blockPos, CollisionContext.empty())) {

                            world.setBlockAndUpdate(blockPos, blockstate);
                            world.scheduleTick(blockPos, Blocks.FROSTED_ICE, Mth.nextInt(player.getRandom(), 60, 120));
                        }
                    }
                }
            }

            BlockState iceBlock = Blocks.ICE.defaultBlockState();
            ((ServerLevel) world).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, iceBlock), player.getX(), player.getY(), player.getZ(), 1, 0.5D, 1D, 0.5D, 0.1);
        }
    }


    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

}