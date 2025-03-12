package com.cozary.nameless_trinkets.items.special;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.utils.TrinketBundleContents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.math.Fraction;

/**
 * ehe{@link net.minecraft.world.item.BundleItem}
 */
public class TrinketBundle extends BundleItem {
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public TrinketBundle() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .component((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), TrinketBundleContents.EMPTY));
    }

/*    public static float getFullnessDisplay(ItemStack p_150767_) {
        TrinketBundleContents bundlecontents = (TrinketBundleContents)p_150767_.getOrDefault((DataComponentType) ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), TrinketBundleContents.EMPTY);
        return bundlecontents.weight().floatValue();
    }*/

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
        if (clickAction == ClickAction.SECONDARY) {
            TrinketBundleContents bundlecontents = (TrinketBundleContents)itemStack.get((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get());
            if (bundlecontents == null) {
                return false;
            } else {
                ItemStack itemstack = slot.getItem();
                TrinketBundleContents.Mutable bundlecontents$mutable = new TrinketBundleContents.Mutable(bundlecontents);
                if (itemstack.isEmpty()) {
                    this.playRemoveOneSound(player);
                    ItemStack itemstack1 = bundlecontents$mutable.removeOne();
                    if (itemstack1 != null) {
                        ItemStack itemstack2 = slot.safeInsert(itemstack1);
                        bundlecontents$mutable.tryInsert(itemstack2);
                    }
                } else if (itemstack.getItem().canFitInsideContainerItems()) {
                    int i = bundlecontents$mutable.tryTransfer(slot, player);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                itemStack.set((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), bundlecontents$mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack itemStack, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if (clickAction == ClickAction.SECONDARY && slot.allowModification(player)) {
            TrinketBundleContents bundlecontents = (TrinketBundleContents)itemStack.get((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get());
            if (bundlecontents == null) {
                return false;
            } else {
                TrinketBundleContents.Mutable bundlecontents$mutable = new TrinketBundleContents.Mutable(bundlecontents);
                if (other.isEmpty()) {
                    ItemStack itemstack = bundlecontents$mutable.removeOne();
                    if (itemstack != null) {
                        this.playRemoveOneSound(player);
                        slotAccess.set(itemstack);
                    }
                } else {
                    int i = bundlecontents$mutable.tryInsert(other);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                itemStack.set((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), bundlecontents$mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (dropContents(itemstack, player)) {
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        TrinketBundleContents bundlecontents = (TrinketBundleContents)itemStack.getOrDefault((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), TrinketBundleContents.EMPTY);
        return bundlecontents.weight().compareTo(Fraction.ZERO) > 0;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        TrinketBundleContents bundlecontents = (TrinketBundleContents)itemStack.getOrDefault((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), TrinketBundleContents.EMPTY);
        return (int)Math.min(bundlecontents.weight().doubleValue() * 64.0D * (13.0D / (double)64), 13.0D);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return BAR_COLOR;
    }

    private static boolean dropContents(ItemStack itemStack, Player player) {
        TrinketBundleContents bundlecontents = (TrinketBundleContents)itemStack.get((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get());
        if (bundlecontents != null && !bundlecontents.isEmpty()) {
            itemStack.set((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), TrinketBundleContents.EMPTY);
            if (player instanceof ServerPlayer) {
                bundlecontents.itemsCopy().forEach((p_327106_) -> {
                    player.drop(p_327106_, true);
                });
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        TrinketBundleContents bundlecontents = (TrinketBundleContents)itemStack.get((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get());
        if (bundlecontents != null) {
            int i = Mth.mulAndTruncate(bundlecontents.weight(), 64);
            tooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", new Object[]{i, 64}).withStyle(ChatFormatting.GRAY));
        }

    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        return !itemStack.has(DataComponents.HIDE_TOOLTIP) && !itemStack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP) ? Optional.ofNullable((TrinketBundleContents)itemStack.get(ModDataComponents.TRINKET_BUNDLE_CONTENTS.get())).map(BundleTooltip::new) : Optional.empty();
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        TrinketBundleContents bundlecontents = (TrinketBundleContents)itemEntity.getItem().get((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get());
        if (bundlecontents != null) {
            itemEntity.getItem().set((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get(), TrinketBundleContents.EMPTY);
            ItemUtils.onContainerDestroyed(itemEntity, bundlecontents.itemsCopy());
        }

    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

 /*   public static boolean isEmpty(ItemStack itemStack) {
        return ((TrinketBundleContents)Objects.requireNonNull((TrinketBundleContents)itemStack.getComponents().get((DataComponentType)ModDataComponents.TRINKET_BUNDLE_CONTENTS.get()))).isEmpty();
    }*/
}
