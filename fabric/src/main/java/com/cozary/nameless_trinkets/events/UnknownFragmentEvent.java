package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static com.cozary.nameless_trinkets.init.ModTags.RECYCLABLE_TRINKETS_TAG;

public class UnknownFragmentEvent {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            UnknownFragmentHandler.onBlockUse(player, level, hitResult.getBlockPos(), hand);
            return InteractionResult.PASS;
        });
    }

}
