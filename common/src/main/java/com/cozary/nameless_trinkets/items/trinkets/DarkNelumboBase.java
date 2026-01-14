package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class DarkNelumboBase extends TrinketItem<DarkNelumboBase.Stats> {
    public static DarkNelumboBase INSTANCE;

    public DarkNelumboBase() {
        super(new TrinketData("dark_nelumbo", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("tooltip.nameless_trinkets.dark_nelumbo_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        if (Minecraft.getInstance().hasShiftDown()) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.dark_nelumbo_1").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
        }
    }

    public static class Stats extends TrinketsStats {
        public boolean cancelLavaDamage = true;
        public boolean isEnable = true;

    }
}
