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

public class LightGlovesBase extends TrinketItem<LightGlovesBase.Stats> {
    public static LightGlovesBase INSTANCE;

    public LightGlovesBase() {
        super(new TrinketData("light_gloves", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = LightGlovesBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.light_gloves_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.light_gloves_1", config.miningSpeedPercentage + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.accept(Component.translatable(ChatFormatting.GRAY + "Suggested By: emu"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float miningSpeedPercentage = 200.0F;
        public boolean isEnable = true;

    }

}