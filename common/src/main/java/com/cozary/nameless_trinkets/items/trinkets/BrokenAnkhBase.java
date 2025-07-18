package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
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

public class BrokenAnkhBase extends TrinketItem<BrokenAnkhBase.Stats> {

    public static BrokenAnkhBase INSTANCE;

    public BrokenAnkhBase() {
        super(new TrinketData("broken_ankh", null, null, Stats.class));

        INSTANCE = this;
    }

    public static int getCooldown(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.BROKEN_ANKH_COOLDOWN.get(), 0);
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.set(ModDataComponents.BROKEN_ANKH_COOLDOWN.get(), cooldown);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = BrokenAnkhBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.broken_ankh_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.broken_ankh_1", ((config.cooldown / 60) / 20)).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int cooldown = 36000;
        public boolean isEnable = true;

    }

}