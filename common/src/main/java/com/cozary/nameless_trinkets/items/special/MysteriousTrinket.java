package com.cozary.nameless_trinkets.items.special;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static com.cozary.nameless_trinkets.init.ModTags.NAMELESS_TRINKETS_TAG;

public class MysteriousTrinket extends Item {

    public MysteriousTrinket() {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .stacksTo(64));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Random random = new Random();

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WOOL_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide) {
            List<Holder<Item>> trinketItems = BuiltInRegistries.ITEM.getOrCreateTag(NAMELESS_TRINKETS_TAG).stream().toList();
            Item selectedTrinket = trinketItems.get(random.nextInt(trinketItems.size())).value();
            BlockPos playerPos = player.getOnPos();

            if (selectedTrinket != null) {
                ServerLevel serverLevel = (ServerLevel) player.getCommandSenderWorld();
                spawnParticles(serverLevel, player);
                spawnItemEntity(serverLevel, selectedTrinket, playerPos);
                maybeAddTrinketSlot(player, random);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    private void spawnParticles(ServerLevel serverLevel, Player player) {
        serverLevel.sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
        serverLevel.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
    }

    private void spawnItemEntity(Level level, Item trinketItem, BlockPos pos) {
        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(), trinketItem.getDefaultInstance());
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    private void maybeAddTrinketSlot(Player player, Random random) {
        double maxSlots = ConfigurationHandler.GENERAL.trinketSlots.get();
        var map = HashMultimap.<String, AttributeModifier>create();
        int currentSlotsQuantity = AccessoriesCapability.get(player).getSlotModifiers().get("trinket_slot").size();

        if (random.nextInt(100) <= ConfigurationHandler.GENERAL.slotProbability.get()) {
            if (currentSlotsQuantity < maxSlots) {
                map.put("trinket", new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "additional_trinkets"), 1, AttributeModifier.Operation.ADD_VALUE));
                AccessoriesCapability.get(player).addPersistentSlotModifiers(map);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.mysterious_trinket_1").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.mysterious_trinket_2").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.mysterious_trinket_3", Component.translatable(String.valueOf(ConfigurationHandler.GENERAL.slotProbability.get()))).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.mysterious_trinket_4", Component.translatable(String.valueOf(ConfigurationHandler.GENERAL.trinketSlots.get()))).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));


    }

}
