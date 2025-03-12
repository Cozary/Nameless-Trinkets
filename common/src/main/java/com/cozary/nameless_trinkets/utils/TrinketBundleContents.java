package com.cozary.nameless_trinkets.utils;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;

import static com.cozary.nameless_trinkets.init.ModTags.NAMELESS_TRINKETS_TAG;

public class TrinketBundleContents extends BundleContents {
    public static final TrinketBundleContents EMPTY = new TrinketBundleContents(List.of());
    public static final Codec<TrinketBundleContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, TrinketBundleContents> STREAM_CODEC;
    private static final Fraction BUNDLE_IN_BUNDLE_WEIGHT;
    public final List<ItemStack> items;
    final Fraction weight;

    TrinketBundleContents(List<ItemStack> itemStacks, Fraction fraction) {
        super(itemStacks);
        this.items = itemStacks;
        this.weight = fraction;
    }

    public TrinketBundleContents(List<ItemStack> list) {
        this(list, computeContentWeight(list));
    }

    private static Fraction computeContentWeight(List<ItemStack> p_336274_) {
        Fraction fraction = Fraction.ZERO;

        ItemStack itemstack;
        for(Iterator var2 = p_336274_.iterator(); var2.hasNext(); fraction = fraction.add(getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)))) {
            itemstack = (ItemStack)var2.next();
        }

        return fraction;
    }

    static Fraction getWeight(ItemStack itemStack) {
        return Fraction.getFraction(1, 64);
    }
    @Override
    public ItemStack getItemUnsafe(int index) {
        return (ItemStack)this.items.get(index);
    }
    @Override
    public Stream<ItemStack> itemCopyStream() {
        return this.items.stream().map(ItemStack::copy);
    }
    @Override
    public Iterable<ItemStack> items() {
        return this.items;
    }

    public List<ItemStack> itemList() {
        return this.items;
    }
    @Override
    public Iterable<ItemStack> itemsCopy() {
        return Lists.transform(this.items, ItemStack::copy);
    }
    @Override
    public int size() {
        return this.items.size();
    }
    @Override
    public Fraction weight() {
        return this.weight;
    }
    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            boolean var10000;
            if (other instanceof TrinketBundleContents) {
                TrinketBundleContents TrinketBundleContents = (TrinketBundleContents)other;
                var10000 = this.weight.equals(TrinketBundleContents.weight) && ItemStack.listMatches(this.items, TrinketBundleContents.items);
            } else {
                var10000 = false;
            }

            return var10000;
        }
    }
    @Override
    public int hashCode() {
        return ItemStack.hashStackList(this.items);
    }
    @Override
    public String toString() {
        return "TrinketBundleContents" + String.valueOf(this.items);
    }

    static {
        CODEC = ItemStack.CODEC.listOf().xmap(TrinketBundleContents::new, (trinketBundleContents) -> {
            return trinketBundleContents.items;
        });
        STREAM_CODEC = ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(TrinketBundleContents::new, (trinketBundleContents) -> {
            return trinketBundleContents.items;
        });
        BUNDLE_IN_BUNDLE_WEIGHT = Fraction.getFraction(1, 16);
    }

    public static class Mutable {
        public final List<ItemStack> items;
        private Fraction weight;

        public Mutable(TrinketBundleContents trinketBundleContents) {
            this.items = new ArrayList(trinketBundleContents.items);
            this.weight = trinketBundleContents.weight;
        }

        public TrinketBundleContents.Mutable clearItems() {
            this.items.clear();
            this.weight = Fraction.ZERO;
            return this;
        }

        private int findStackIndex(ItemStack itemStack) {
            if (!itemStack.isStackable()) {
                return -1;
            } else {
                for(int i = 0; i < this.items.size(); ++i) {
                    if (ItemStack.isSameItemSameComponents((ItemStack)this.items.get(i), itemStack)) {
                        return i;
                    }
                }

                return -1;
            }
        }

        private int getMaxAmountToAdd(ItemStack itemStack) {
            return itemStack.is(NAMELESS_TRINKETS_TAG) ? (int)((float)64 - this.weight.floatValue() * 64.0F) : 0;
        }

        public int tryInsert(ItemStack itemStack) {
            if (!itemStack.isEmpty() && itemStack.getItem().canFitInsideContainerItems()) {
                int i = Math.min(itemStack.getCount(), this.getMaxAmountToAdd(itemStack));
                if (i == 0) {
                    return 0;
                } else {
                    this.weight = this.weight.add(TrinketBundleContents.getWeight(itemStack).multiplyBy(Fraction.getFraction(i, 1)));
                    int j = this.findStackIndex(itemStack);
                    if (j != -1) {
                        ItemStack itemstack = (ItemStack)this.items.remove(j);
                        ItemStack itemstack1 = itemstack.copyWithCount(itemstack.getCount() + i);
                        itemStack.shrink(i);
                        this.items.add(0, itemstack1);
                    } else {
                        this.items.add(0, itemStack.split(i));
                    }

                    return i;
                }
            } else {
                return 0;
            }
        }

        public int tryTransfer(Slot slot, Player player) {
            ItemStack itemstack = slot.getItem();
            int i = this.getMaxAmountToAdd(itemstack);
            return this.tryInsert(slot.safeTake(itemstack.getCount(), i, player));
        }

        public ItemStack removeOne() {
            if (this.items.isEmpty()) {
                return null;
            } else {
                ItemStack itemstack = ((ItemStack)this.items.remove(0)).copy();
                this.weight = this.weight.subtract(TrinketBundleContents.getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)));
                return itemstack;
            }
        }

        public Fraction weight() {
            return this.weight;
        }

        public TrinketBundleContents toImmutable() {
            return new TrinketBundleContents(List.copyOf(this.items), this.weight);
        }
    }
}