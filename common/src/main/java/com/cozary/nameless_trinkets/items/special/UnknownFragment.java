package com.cozary.nameless_trinkets.items.special;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

import static com.cozary.nameless_trinkets.utils.CommonUtils.itemId;

public class UnknownFragment extends Item {

    public UnknownFragment() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
                .stacksTo(64)
                .setId(itemId("unknown_fragment"))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("tooltip.nameless_trinkets.unknown_fragment").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
