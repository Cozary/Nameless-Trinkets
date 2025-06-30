package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.LuckyRock;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class LuckyRockHandler {

    public static void function(Player player, BlockState blockState, BlockPos blockPos) {
        LuckyRock.Stats config = LuckyRock.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Level world = player.level();
        Random random = new Random();

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }
        var stack = accessories.getEquipped(ModItems.LUCKY_ROCK.get());

        if (!stack.isEmpty() && random.nextInt(100) <= config.percentageOfObtaining && config.blockList.contains(BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString()) && !player.level().isClientSide) {
            String itemStack = config.itemList.get(random.nextInt(config.itemList.size()));

            assert itemStack != null;
            ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.HAPPY_VILLAGER, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 25, 1D, 1D, 1D, 0.1);
            ItemEntity itementity = new ItemEntity(world, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemStack)).getDefaultInstance());
            itementity.setDefaultPickUpDelay();
            world.addFreshEntity(itementity);
        }
    }
}
