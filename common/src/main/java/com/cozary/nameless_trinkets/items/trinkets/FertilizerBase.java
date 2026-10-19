package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FertilizerBase extends TrinketItem<FertilizerBase.Stats> {

    private static final int UNDERWATER_SURFACE_BUFFER = 2;
    public static FertilizerBase INSTANCE;

    public FertilizerBase() {
        super(new TrinketData("fertilizer", null, null, Stats.class));

        INSTANCE = this;
    }

    private static boolean hasWaterAbove(Level level, BlockPos pos, int waterBlocks) {
        for (int i = 1; i <= waterBlocks; i++) {
            if (!level.getFluidState(pos.above(i)).is(FluidTags.WATER)) {
                return false;
            }
        }
        return true;
    }

    public static boolean growWaterPlant(Level level, BlockPos pos, @Nullable Direction clickedSide) {
        if (!level.getBlockState(pos).is(Blocks.WATER) || level.getFluidState(pos).getAmount() != 8) {
            return false;
        }

        if (!hasWaterAbove(level, pos, UNDERWATER_SURFACE_BUFFER)) {
            return false;
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return true;
        }

        RandomSource random = level.getRandom();
        boolean success = false;

        for (int i = 0; i < 128; ++i) {
            BlockPos currentPos = pos;

            for (int j = 0; j < i / 16; ++j) {
                currentPos = currentPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1
                );

                BlockState curState = level.getBlockState(currentPos);
                if (!curState.is(Blocks.WATER) && !curState.is(Blocks.KELP) && !curState.is(Blocks.KELP_PLANT)) {
                    break;
                }
            }

            BlockState state = level.getBlockState(currentPos);

            if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT)) {

                if (hasWaterAbove(level, currentPos, UNDERWATER_SURFACE_BUFFER)) {
                    growKelp(level, currentPos);
                    success = true;
                }
            } else if (state.is(Blocks.WATER) && level.getFluidState(currentPos).getAmount() == 8) {

                if (hasWaterAbove(level, currentPos, UNDERWATER_SURFACE_BUFFER)) {
                    if (applyBiomeModifiers(level, currentPos, random, clickedSide, Blocks.SEAGRASS.defaultBlockState())) {
                        success = true;
                    }
                }
            }
        }

        return success;
    }

    private static boolean applyBiomeModifiers(Level level, BlockPos pos, RandomSource random, @Nullable Direction clickedSide, BlockState newState) {
        Holder<Biome> biome = level.getBiome(pos);
        if (biome.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
            if (random.nextInt(4) == 0) {
                newState = BuiltInRegistries.BLOCK
                        .getRandomElementOf(BlockTags.UNDERWATER_BONEMEALS, random)
                        .map((block) -> ((Block) block.value()).defaultBlockState())
                        .orElse(newState);
            }
        }

        if (newState.canSurvive(level, pos)) {
            level.setBlock(pos, newState, 3);
            return true;
        }

        return false;
    }

    private static void growKelp(Level level, BlockPos pos) {
        BlockPos abovePos = pos.above();


        if (!hasWaterAbove(level, abovePos, UNDERWATER_SURFACE_BUFFER)) {
            return;
        }

        if (level.getBlockState(abovePos).is(Blocks.WATER) && level.getFluidState(abovePos).getAmount() == 8) {
            level.setBlock(abovePos, Blocks.KELP_PLANT.defaultBlockState(), 3);
        }
    }

    static boolean applyBonemeal(Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof BonemealableBlock bonemealable &&
                bonemealable.isValidBonemealTarget(level, pos, blockState)) {
            if (level instanceof ServerLevel serverLevel && bonemealable.isBonemealSuccess(level, level.getRandom(), pos, blockState)) {
                bonemealable.performBonemeal(serverLevel, level.getRandom(), pos, blockState);
                return true;
            }
        }
        return false;
    }

    static void spawnGrowthParticles(LevelAccessor level, BlockPos pos, int count) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    count, 0.25D, 0.25D, 0.25D, 0.05D
            );
        }
    }

    private static boolean isValidBonemealTarget(Level level, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof BonemealableBlock bonemealable) {
            return bonemealable.isValidBonemealTarget(level, pos, state);
        }
        return false;
    }

    public void commonTick(ItemStack stack, LivingEntity entity) {
        Stats config = getTrinketConfig();
        Level level = entity.level();

        if (level.isClientSide()) return;
        if (!config.isEnable) return;


        int effectInterval = Math.max(1, config.effectIntervalInTicks);
        if (entity.tickCount % effectInterval != 0) return;

        RandomSource random = level.getRandom();
        BlockPos playerPos = entity.blockPosition();

        final int rx = 4;
        final int ryDown = 2;
        final int ryUp = 2;

        ArrayList<BlockPos> priority = new ArrayList<>();
        ArrayList<BlockPos> bonemealable = new ArrayList<>();
        ArrayList<BlockPos> waterSources = new ArrayList<>();

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -rx; dx <= rx; dx++) {
            for (int dz = -rx; dz <= rx; dz++) {
                for (int dy = -ryDown; dy <= ryUp; dy++) {
                    cursor.set(playerPos.getX() + dx, playerPos.getY() + dy, playerPos.getZ() + dz);

                    BlockState st = level.getBlockState(cursor);

                    if (st.isAir()) continue;

                    if (st.is(BlockTags.CROPS) || st.is(BlockItemTags.SAPLINGS.block())) {
                        if (isValidBonemealTarget(level, cursor, st)) {
                            priority.add(cursor.immutable());
                        }
                        continue;
                    }

                    if (isValidBonemealTarget(level, cursor, st)) {
                        bonemealable.add(cursor.immutable());
                        continue;
                    }

                    if (level.getFluidState(cursor).is(FluidTags.WATER)
                            && level.getFluidState(cursor).getAmount() == 8
                            && st.is(Blocks.WATER)) {

                        if (hasWaterAbove(level, cursor, UNDERWATER_SURFACE_BUFFER)) {
                            waterSources.add(cursor.immutable());
                        }
                    }
                }
            }
        }

        BlockPos targetPos = null;
        String targetBucket = "none";

        if (!priority.isEmpty()) {
            targetPos = priority.get(random.nextInt(priority.size()));
            targetBucket = "crops/saplings";
        } else if (!bonemealable.isEmpty()) {
            targetPos = bonemealable.get(random.nextInt(bonemealable.size()));
            targetBucket = "bonemealable";
        } else if (!waterSources.isEmpty()) {
            targetPos = waterSources.get(random.nextInt(waterSources.size()));
            targetBucket = "waterSource";
        } else {
            if (config.debugLogging) {
                NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} result=NO_TARGET at={}", entity.getName().getString(), targetBucket, playerPos);
            }
            return;
        }

        BlockState targetState = level.getBlockState(targetPos);
        boolean isCrop = targetState.is(BlockTags.CROPS);
        boolean isSapling = targetState.is(BlockItemTags.SAPLINGS.block());

        boolean success = false;

        if (targetBucket.equals("crops/saplings") || targetBucket.equals("bonemealable")) {
            success = applyBonemeal(level, targetPos);

            if (!success && (isCrop || isSapling) && random.nextFloat() < 0.35f) {
                success = applyBonemeal(level, targetPos);
            }

            if (success) {
                playFertilizerEffect(level, targetPos);
                if (config.debugLogging) {
                    NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} target={} block={} crop={} sapling={} result=SUCCESS", entity.getName().getString(), targetBucket, targetPos, BuiltInRegistries.BLOCK.getKey(targetState.getBlock()), isCrop, isSapling);
                }
                return;
            }
        }

        if (targetBucket.equals("waterSource")) {
            if (!hasWaterAbove(level, targetPos, UNDERWATER_SURFACE_BUFFER)) {
                if (config.debugLogging) {
                    NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} target={} result=SKIP_NEAR_SURFACE", entity.getName().getString(), targetBucket, targetPos);
                }
                return;
            }

            if (growWaterPlant(level, targetPos, null)) {
                playFertilizerEffect(level, targetPos);
                if (config.debugLogging) {
                    NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} target={} result=SUCCESS (waterSpread)", entity.getName().getString(), targetBucket, targetPos);
                }
                return;
            }
        }

        if (config.debugLogging) {
            NamelessTrinkets.LOG.info("[Nameless-Trinkets][Fertilizer] attempt: entity={} bucket={} target={} block={} crop={} sapling={} result=FAIL", entity.getName().getString(), targetBucket, targetPos, BuiltInRegistries.BLOCK.getKey(targetState.getBlock()), isCrop, isSapling);
        }
    }

    private void playFertilizerEffect(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 0.4f, 1.0f + (level.getRandom().nextFloat() * 0.2f));

        spawnGrowthParticles(level, pos, 15);

        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 3, 0.25, 0.25, 0.25, 0.02);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = FertilizerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fertilizer_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fertilizer_1").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fertilizer_2", config.effectIntervalInTicks / 20).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int effectIntervalInTicks = 100;
        public boolean isEnable = true;
        public boolean debugLogging = true;
    }

}
