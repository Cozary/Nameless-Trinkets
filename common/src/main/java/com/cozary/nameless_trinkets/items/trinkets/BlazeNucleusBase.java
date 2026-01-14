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

public class BlazeNucleusBase extends TrinketItem<BlazeNucleusBase.Stats> {
    public static BlazeNucleusBase INSTANCE;

    public BlazeNucleusBase() {
        super(new TrinketData("blaze_nucleus", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = BlazeNucleusBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                if (config.fireDamageReductionPercentage == 100) {
                    tooltip.accept(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_1").withStyle(ChatFormatting.GOLD));
                } else {
                    tooltip.accept(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_2", config.fireDamageReductionPercentage + "%").withStyle(ChatFormatting.GOLD));
                }
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_3").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int setEnemyInFireTicks = 300;
        public float fireDamageReductionPercentage = 80.0F;
        public boolean isEnable = true;

    }

}