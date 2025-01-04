package com.cozary.nameless_trinkets.items.special;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnknownFragment extends Item {

    public UnknownFragment() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
                .stacksTo(64)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "unknown_fragment")))
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.unknown_fragment").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
