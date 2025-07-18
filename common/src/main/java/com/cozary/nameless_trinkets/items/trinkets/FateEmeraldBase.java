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

public class FateEmeraldBase extends TrinketItem<FateEmeraldBase.Stats> {
    public static FateEmeraldBase INSTANCE;

    public FateEmeraldBase() {
        super(new TrinketData("fate_emerald", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fate_emerald_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fate_emerald_1").withStyle(ChatFormatting.GOLD));

                String formattedSeconds = String.format("%.2f", config.timeUntilUnequip / 20.0);
                String formattedHunger = String.format("%.2f", config.hungerExhaustionRate);

                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fate_emerald_2", formattedSeconds).withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.fate_emerald_3", formattedHunger).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int discountBoost = 100;
        public float timeUntilUnequip = 1200;
        public float hungerExhaustionRate = 0.1F;
        public boolean isEnable = true;
        float backupTimeUntilUnequip = 0;
        boolean resetTimerFlag = false;
    }


}