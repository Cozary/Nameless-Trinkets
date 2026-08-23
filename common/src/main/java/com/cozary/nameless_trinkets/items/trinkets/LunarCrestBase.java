package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.LunarCrestHandler;
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

public class LunarCrestBase extends TrinketItem<LunarCrestBase.Stats> {
    public static LunarCrestBase INSTANCE;

    public LunarCrestBase() {
        super(new TrinketData("lunar_crest", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = LunarCrestBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.lunar_crest_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.lunar_crest_1",
                        String.format("%.0f", config.maxMoonDamagePercentage) + "%",
                        String.format("%.0f", LunarCrestHandler.getCurrentClientBonus()) + "%").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.lunar_crest_2",
                        String.format("%.0f", config.wolfDamageMultiplierPercentage) + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public double baseMoonDamagePercentage = 0.0;
        public double maxMoonDamagePercentage = 100.0;
        public double wolfDamageMultiplierPercentage = 50.0;
        public boolean isEnable = true;
    }
}
