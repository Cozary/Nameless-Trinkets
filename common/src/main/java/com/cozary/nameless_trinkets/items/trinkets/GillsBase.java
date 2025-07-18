package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class GillsBase extends TrinketItem<GillsBase.Stats> {
    public static GillsBase INSTANCE;

    public GillsBase() {
        super(new TrinketData("gills", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        if (!trinketConfig.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gills_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gills_1").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gills_2").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gills_3").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float chokingDamage = 2.0F;
        public boolean blindnessWhenChoking = true;
        public int airReductionSpeed = 7;
        public boolean isEnable = true;

    }
}