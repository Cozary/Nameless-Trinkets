package com.cozary.nameless_trinkets.util;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

import static com.cozary.nameless_trinkets.init.ModTags.RECYCLABLE_TRINKETS_TAG;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class UnknownFragmentEvent {

    @SubscribeEvent
    public static void onBlockUse(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos blockpos = event.getPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Player player = event.getEntity();

        if (ConfigurationHandler.GENERAL.getFragments.get()) {
            if (!level.isClientSide && blockstate.getBlock() == Blocks.AMETHYST_BLOCK) {
                Item itemstack = ModItems.UNKNOWN_FRAGMENT.get();

                List<Holder<Item>> trinketItems = BuiltInRegistries.ITEM.getOrThrow(RECYCLABLE_TRINKETS_TAG).stream().toList();

                for (Holder<Item> trinketItemHolder : trinketItems) {
                    Item trinketItem = trinketItemHolder.value();

                    if (player.getMainHandItem().getItem() == trinketItem) {
                        ItemStack itemstack1 = player.getItemInHand(event.getHand());
                        itemstack1.shrink(1);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));

                        ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.ENCHANT, blockpos.getX() + 0.5, blockpos.getY(), blockpos.getZ() + 0.5, 200, 1D, 1D, 1D, 0.1);
                        ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.GLOW, blockpos.getX() + 0.5, blockpos.getY(), blockpos.getZ() + 0.5, 100, 1D, 1D, 1D, 0.1);

                        BlockPos pos = player.blockPosition();
                        ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(), itemstack.getDefaultInstance());
                        itementity.setDefaultPickUpDelay();
                        level.addFreshEntity(itementity);

                        return;
                    }
                }
            }
        }
    }

}
