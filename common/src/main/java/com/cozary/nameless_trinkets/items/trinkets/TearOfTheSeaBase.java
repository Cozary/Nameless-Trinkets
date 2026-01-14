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

public class TearOfTheSeaBase extends TrinketItem<TearOfTheSeaBase.Stats> {
    public static TearOfTheSeaBase INSTANCE;

    public TearOfTheSeaBase() {
        super(new TrinketData("tear_of_the_sea", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = TearOfTheSeaBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.tear_of_the_sea_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.tear_of_the_sea_1", config.swimSpeedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.accept(Component.translatable(ChatFormatting.GRAY + "Suggested By: JayOnline_"));
            }
        }
    }


    public static class Stats extends TrinketsStats {
        public double swimSpeedMultiplierPercentage = 50.0f;
        public boolean isEnable = true;

    }
}