package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
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
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Random;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LuckyRockEvents {

    @SubscribeEvent
    public static void function(BlockEvent.BreakEvent event) {
        LuckyRock.Stats config = LuckyRock.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Player player = event.getPlayer();
        Level world = player.level();
        Random random = new Random();

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.LUCKY_ROCK.get());

        if (!stack.isEmpty() && random.nextInt(100) <= config.percentageOfObtaining && event.getState() == Blocks.STONE.defaultBlockState() && !player.level().isClientSide) {
            String itemStack = config.blockList.get(random.nextInt(config.blockList.size()));

            BlockPos pos = event.getPos();
            assert itemStack != null;
            ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 25, 1D, 1D, 1D, 0.1);
            ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemStack)).getDefaultInstance());
            itementity.setDefaultPickUpDelay();
            world.addFreshEntity(itementity);
        }
    }
}
