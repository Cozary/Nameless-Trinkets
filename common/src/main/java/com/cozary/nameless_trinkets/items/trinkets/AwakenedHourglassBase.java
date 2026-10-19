package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.ShatteredHourglassHandler;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class AwakenedHourglassBase extends TrinketItem<AwakenedHourglassBase.Stats> {

    private final ShatteredHourglassHandler.TemporalState temporalState;

    public AwakenedHourglassBase(String id, ShatteredHourglassHandler.TemporalState temporalState) {
        super(new TrinketData(id, null, Rarity.EPIC, Stats.class));
        this.temporalState = temporalState;
    }

    public ShatteredHourglassHandler.TemporalState getTemporalState() {
        return temporalState;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
            return;
        }

        String loreKey = switch (temporalState) {
            case TIME_STOP -> "tooltip.nameless_trinkets.awakened_hourglass_time_stop_lore";
            case ACCELERATION -> "tooltip.nameless_trinkets.awakened_hourglass_acceleration_lore";
            case DILATION -> "tooltip.nameless_trinkets.awakened_hourglass_dilation_lore";
            default -> "tooltip.nameless_trinkets.awakened_hourglass_lore";
        };
        tooltip.accept(Component.translatable(loreKey).withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));

        if (Minecraft.getInstance().hasShiftDown()) {
            if (config.isPermanent) {
                switch (temporalState) {
                    case TIME_STOP ->
                            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.awakened_hourglass_time_stop_permanent")
                                    .withStyle(ChatFormatting.GOLD));
                    case ACCELERATION ->
                            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.awakened_hourglass_acceleration_permanent",
                                    String.format("%.0f", config.acceleratedTickRate)).withStyle(ChatFormatting.GOLD));
                    case DILATION ->
                            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.awakened_hourglass_dilation_permanent",
                                    String.format("%.0f", config.dilatedTickRate)).withStyle(ChatFormatting.GOLD));
                    default -> {
                    }
                }
            } else {
                switch (temporalState) {
                    case TIME_STOP ->
                            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.awakened_hourglass_time_stop_1",
                                    String.format("%.0f", config.effectDurationSeconds),
                                    String.format("%.0f", config.cooldownSeconds)).withStyle(ChatFormatting.GOLD));
                    case ACCELERATION ->
                            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.awakened_hourglass_acceleration_1",
                                    String.format("%.0f", config.acceleratedTickRate),
                                    String.format("%.0f", config.effectDurationSeconds),
                                    String.format("%.0f", config.cooldownSeconds)).withStyle(ChatFormatting.GOLD));
                    case DILATION ->
                            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.awakened_hourglass_dilation_1",
                                    String.format("%.0f", config.dilatedTickRate),
                                    String.format("%.0f", config.effectDurationSeconds),
                                    String.format("%.0f", config.cooldownSeconds)).withStyle(ChatFormatting.GOLD));
                    default -> {
                    }
                }
            }
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
        }
    }

    public static class Stats extends TrinketsStats {
        public boolean isPermanent = true;
        public double effectDurationSeconds = 10.0;
        public double cooldownSeconds = 50.0;
        public float acceleratedTickRate = 50.0f;
        public float dilatedTickRate = 5.0f;
        public boolean isEnable = true;
    }
}
