package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Fertilizer extends TrinketItem<Fertilizer.Stats> {
    public static Fertilizer INSTANCE;

    public Fertilizer() {
        super(new TrinketData(new Item.Properties().stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "fertilizer")))
                , null,
                Stats.class));
        INSTANCE = this;
    }

    public static boolean growWaterPlant(Level level, BlockPos pos, @Nullable Direction clickedSide) {
        if (!level.getBlockState(pos).is(Blocks.WATER) || level.getFluidState(pos).getAmount() != 8) {
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
                currentPos = currentPos.offset(
                        random.nextInt(3) - 1,
                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1
                );

                if (!level.getBlockState(currentPos).is(Blocks.WATER) && !level.getBlockState(currentPos).is(Blocks.KELP) && !level.getBlockState(currentPos).is(Blocks.KELP_PLANT)) {
                    break;
                }
            }

            BlockState state = level.getBlockState(currentPos);

            if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT)) {
                growKelp(level, currentPos);
                success = true;
            } else if (state.is(Blocks.WATER) && level.getFluidState(currentPos).getAmount() == 8) {
                if (applyBiomeModifiers(level, currentPos, random, clickedSide, Blocks.SEAGRASS.defaultBlockState())) {
                    success = true;
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
        if (level.getBlockState(abovePos).is(Blocks.WATER) && level.getFluidState(abovePos).getAmount() == 8) {
            level.setBlock(abovePos, Blocks.KELP_PLANT.defaultBlockState(), 3);
        }
    }

    private static boolean applyBonemeal(Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof BonemealableBlock bonemealable &&
                bonemealable.isValidBonemealTarget(level, pos, blockState)) {
            if (level instanceof ServerLevel serverLevel && bonemealable.isBonemealSuccess(level, level.random, pos, blockState)) {
                bonemealable.performBonemeal(serverLevel, level.random, pos, blockState);
                return true;
            }
        }
        return false;
    }

    private static void spawnGrowthParticles(LevelAccessor level, BlockPos pos, int count) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    count, 0.25D, 0.25D, 0.25D, 0.05D
            );
        }
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        super.tick(stack, reference);

        Stats config = Fertilizer.INSTANCE.getTrinketConfig();
        LivingEntity entity = reference.entity();
        Level level = entity.level();

        if (level.isClientSide) return;
        if (!config.isEnable) return;

        // Run once every N ticks (default = 100 ticks = 5s)
        int effectInterval = Math.max(1, config.effectIntervalInTicks);
        if (entity.tickCount % effectInterval != 0) return;

        RandomSource random = level.getRandom();
        BlockPos playerPos = entity.blockPosition();

        // --- Scan a small area around the player and prioritize crops/saplings ---
        // Radius choices: keep small to stay cheap. (Only runs once per 10s anyway.)
        final int rx = 4;     // X/Z radius
        final int ryDown = 2; // scan below
        final int ryUp = 2;   // scan above

        // We keep three candidate lists:
        // 1) crops/saplings first
        // 2) any bonemealable blocks second
        // 3) water-source blocks last (for underwater plant spread)
        java.util.ArrayList<BlockPos> priority = new java.util.ArrayList<>();
        java.util.ArrayList<BlockPos> bonemealable = new java.util.ArrayList<>();
        java.util.ArrayList<BlockPos> waterSources = new java.util.ArrayList<>();

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -rx; dx <= rx; dx++) {
            for (int dz = -rx; dz <= rx; dz++) {
                for (int dy = -ryDown; dy <= ryUp; dy++) {
                    cursor.set(playerPos.getX() + dx, playerPos.getY() + dy, playerPos.getZ() + dz);

                    BlockState st = level.getBlockState(cursor);

                    // Skip air to reduce noise
                    if (st.isAir()) continue;

                    // Priority 1: tagged crops or saplings (modded-friendly)
                    if (st.is(BlockTags.CROPS) || st.is(BlockTags.SAPLINGS)) {
                        if (isValidBonemealTarget(level, cursor, st)) {
                            priority.add(cursor.immutable());
                        }
                        continue;
                    }

                    // Priority 2: anything bonemealable
                    if (isValidBonemealTarget(level, cursor, st)) {
                        bonemealable.add(cursor.immutable());
                        continue;
                    }

                    // Priority 3: water sources (for kelp/seagrass spread behavior)
                    if (level.getFluidState(cursor).is(net.minecraft.tags.FluidTags.WATER)
                            && level.getFluidState(cursor).getAmount() == 8
                            && st.is(Blocks.WATER)) {
                        waterSources.add(cursor.immutable());
                    }
                }
            }
        }

        // Pick a target with priority:
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
            // Nothing useful found in scan radius; do nothing (avoids random spam)
            if (config.debugLogging) {
                NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} result=NO_TARGET at={}",
                        entity.getName().getString(), targetBucket, playerPos);
            }
            return;
        }

        BlockState targetState = level.getBlockState(targetPos);
        boolean isCrop = targetState.is(BlockTags.CROPS);
        boolean isSapling = targetState.is(BlockTags.SAPLINGS);

        // Attempt 1: bonemeal for crops/saplings/bonemealable
        boolean success = false;

        if (targetBucket.equals("crops/saplings") || targetBucket.equals("bonemealable")) {
            success = applyBonemeal(level, targetPos);

            // Optional: 2nd roll for crops/saplings to make farms feel better
            if (!success && (isCrop || isSapling) && random.nextFloat() < 0.35f) {
                success = applyBonemeal(level, targetPos);
            }

            if (success) {
                playFertilizerEffect(level, targetPos);
                if (config.debugLogging) {
                    NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} target={} block={} crop={} sapling={} result=SUCCESS",
                            entity.getName().getString(),
                            targetBucket,
                            targetPos,
                            net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(targetState.getBlock()),
                            isCrop,
                            isSapling
                    );
                }
                return;
            }
        }

        // Attempt 2: water-source spread (kelp/seagrass/coral behavior)
        if (targetBucket.equals("waterSource")) {
            if (growWaterPlant(level, targetPos, null)) {
                playFertilizerEffect(level, targetPos);
                if (config.debugLogging) {
                    NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} target={} result=SUCCESS (waterSpread)",
                            entity.getName().getString(), targetBucket, targetPos);
                }
                return;
            }
        }

        // If we got here, it failed
        if (config.debugLogging) {
            NamelessTrinkets.LOG.info("[Fertilizer] attempt: entity={} bucket={} target={} block={} crop={} sapling={} result=FAIL",
                    entity.getName().getString(),
                    targetBucket,
                    targetPos,
                    net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(targetState.getBlock()),
                    isCrop,
                    isSapling
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = Fertilizer.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.fertilizer_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fertilizer_1").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fertilizer_2", config.effectIntervalInTicks / 20).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    // creates a sound and particle effect at the given position
    private void playFertilizerEffect(Level level, BlockPos pos) {
        // Sound
        level.playSound(
            null,
            pos,
            net.minecraft.sounds.SoundEvents.BONE_MEAL_USE,
            net.minecraft.sounds.SoundSource.PLAYERS,
            0.4f,
            1.0f + (level.getRandom().nextFloat() * 0.2f)
        );

        // Particles (bonemeal + small sparkle)
        spawnGrowthParticles(level, pos, 15);

        if (level instanceof net.minecraft.server.level.ServerLevel server) {
            server.sendParticles(
                net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5,
                pos.getY() + 0.8,
                pos.getZ() + 0.5,
                3,
                0.25, 0.25, 0.25,
                0.02
            );
        }
    }

    private static boolean isValidBonemealTarget(Level level, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof BonemealableBlock bonemealable) {
            return bonemealable.isValidBonemealTarget(level, pos, state);
        }
        return false;
    }



    public static class Stats extends TrinketsStats {
        public int effectIntervalInTicks = 100;
        public boolean isEnable = true;

        // Turn on/off attempt logs without recompiling
        public boolean debugLogging = true;

    }

}