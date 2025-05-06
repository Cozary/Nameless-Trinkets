package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Fertilizer extends FertilizerBase implements ICurioItem {

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = FertilizerBase.INSTANCE.getTrinketConfig();

        LivingEntity entity = slotContext.entity();
        Level level = entity.level();

        if (level.isClientSide) {
            return;
        }

        RandomSource random = level.getRandom();
        BlockPos playerPos = entity.blockPosition();
        BlockPos targetPos = playerPos.offset(
                random.nextInt(5) - 3,
                random.nextInt(3) - 2,
                random.nextInt(5) - 3
        );

        int effectInterval = config.effectIntervalInTicks;
        if (entity.tickCount % effectInterval == 0) {
            BlockState targetState = level.getBlockState(targetPos);
            BlockState stateBelow = level.getBlockState(playerPos.below());

            if (stateBelow.is(Blocks.GRASS_BLOCK)) {
                if (applyBonemeal(level, targetPos)) {
                    spawnGrowthParticles(level, targetPos, 3);
                }
            } else if (targetState.is(Blocks.WATER)) {
                if (growWaterPlant(level, targetPos, null)) {
                    spawnGrowthParticles(level, targetPos, 3);
                }
            }
        }
    }

}