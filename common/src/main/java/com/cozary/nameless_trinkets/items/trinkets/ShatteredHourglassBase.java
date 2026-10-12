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

public class ShatteredHourglassBase extends TrinketItem<ShatteredHourglassBase.Stats> {
    public static ShatteredHourglassBase INSTANCE;

    public ShatteredHourglassBase() {
        super(new TrinketData("shattered_hourglass", null, null, Stats.class));
        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = ShatteredHourglassBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.shattered_hourglass_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.shattered_hourglass_1",
                        String.format("%.0f", config.minEffectDurationSeconds),
                        String.format("%.0f", config.maxEffectDurationSeconds)).withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.shattered_hourglass_2",
                        String.format("%.0f", config.acceleratedTickRate),
                        String.format("%.0f", config.dilatedTickRate)).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public double minEffectDurationSeconds = 5.0;
        public double maxEffectDurationSeconds = 30.0;
        public double minCooldownSeconds = 30.0;
        public double maxCooldownSeconds = 60.0;
        public float acceleratedTickRate = 50.0f;
        public float dilatedTickRate = 5.0f;
        public boolean isEnable = true;
    }
}
