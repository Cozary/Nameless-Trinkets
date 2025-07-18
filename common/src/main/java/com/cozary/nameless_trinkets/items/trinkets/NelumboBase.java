package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class NelumboBase extends TrinketItem<NelumboBase.Stats> {
    public static NelumboBase INSTANCE;

    public NelumboBase() {
        super(new TrinketData("nelumbo",null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.nelumbo_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.nelumbo_1").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
        }
    }

    public static class Stats extends TrinketsStats {
        public boolean isEnable = true;

    }
}
