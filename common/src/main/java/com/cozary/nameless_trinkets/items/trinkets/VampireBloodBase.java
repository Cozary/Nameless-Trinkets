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

public class VampireBloodBase extends TrinketItem<VampireBloodBase.Stats> {
    public static VampireBloodBase INSTANCE;

    public VampireBloodBase() {
        super(new TrinketData("vampire_blood", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.vampire_blood_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.vampire_blood_1", config.damageMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.vampire_blood_2", config.healingPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.vampire_blood_3").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public double damageMultiplierPercentage = 150.0f;
        public float healingPercentage = 10.0f;
        public double sunDamage = 2.0f;
        public boolean isEnable = true;

    }
}