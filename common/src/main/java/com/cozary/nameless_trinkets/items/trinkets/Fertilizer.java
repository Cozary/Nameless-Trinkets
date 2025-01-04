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
            BlockState newState = Blocks.SEAGRASS.defaultBlockState();

            for (int j = 0; j < i / 16; ++j) {
                currentPos = currentPos.offset(
                        random.nextInt(3) - 1,
                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1
                );
                if (level.getBlockState(currentPos).isCollisionShapeFullBlock(level, currentPos)) {
                    continue;
                }
            }

            if (applyBiomeModifiers(level, currentPos, random, clickedSide, newState)) {
                success = true;
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
                        .map((block) -> {
                            return ((Block) block.value()).defaultBlockState();
                        })
                        .orElse(newState);
            }
        }

        if (newState.canSurvive(level, pos)) {
            level.setBlock(pos, newState, 3);
            return true;
        }
        return false;
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

        LivingEntity entity = reference.entity();
        Level level = entity.level();

        if (!level.isClientSide) {
            return;
        }

        RandomSource random = level.getRandom();
        BlockPos playerPos = entity.blockPosition();
        BlockPos targetPos = playerPos.offset(
                random.nextInt(5) - 3,
                random.nextInt(3) - 2,
                random.nextInt(5) - 3
        );

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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = Fertilizer.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.fertilizer_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fertilizer_1").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {

        public boolean isEnable = true;

    }

}