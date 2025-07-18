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
public class TrueHeartOfTheSeaBase extends TrinketItem<TrueHeartOfTheSeaBase.Stats> {
    public static TrueHeartOfTheSeaBase INSTANCE;

    public TrueHeartOfTheSeaBase() {
        super(new TrinketData("true_heart_of_the_sea", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = TrueHeartOfTheSeaBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.true_heart_of_the_sea_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.true_heart_of_the_sea_1", config.swimSpeedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.true_heart_of_the_sea_2").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.true_heart_of_the_sea_3").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.true_heart_of_the_sea_4", config.miningUnderwaterSpeedPercentage + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }


    public static class Stats extends TrinketsStats {
        public float miningUnderwaterSpeedPercentage = 300.0F;
        public float chokingDamage = 1.5F;
        public boolean blindnessWhenChoking = true;
        public int airReductionSpeed = 5;
        public double swimSpeedMultiplierPercentage = 100.0f;
        public boolean isEnable = true;

    }

}