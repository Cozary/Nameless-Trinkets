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

public class ElectricPaddleBase extends TrinketItem<ElectricPaddleBase.Stats> {
    public static ElectricPaddleBase INSTANCE;

    public ElectricPaddleBase() {
        super(new TrinketData("electric_paddle", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = ElectricPaddleBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.electric_paddle_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.electric_paddle_1", config.boatSpeedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float boatSpeedMultiplierPercentage = 100;
        public boolean isEnable = true;

    }

}