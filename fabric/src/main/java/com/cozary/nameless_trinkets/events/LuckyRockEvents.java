package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.LuckyRock;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class LuckyRockEvents {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, entity) -> {
            LuckyRock.Stats config = LuckyRock.INSTANCE.getTrinketConfig();

            if (!config.isEnable) return;

            if (player != null && !player.isSpectator()) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.LUCKY_ROCK.get());

                Random random = new Random();

                if (!stack.isEmpty() && random.nextInt(100) <= config.percentageOfObtaining && state == Blocks.STONE.defaultBlockState() && !world.isClientSide) {
                    String itemStack = config.blockList.get(random.nextInt(config.blockList.size()));

                    ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 25, 1D, 1D, 1D, 0.1);
                    ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemStack)).getDefaultInstance());
                    itementity.setDefaultPickUpDelay();
                    world.addFreshEntity(itementity);
                }
            }
        }));
    }

}
