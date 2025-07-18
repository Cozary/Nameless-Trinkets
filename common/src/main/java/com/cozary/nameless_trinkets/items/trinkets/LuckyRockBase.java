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

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LuckyRockBase extends TrinketItem<LuckyRockBase.Stats> {
    public static LuckyRockBase INSTANCE;

    public LuckyRockBase() {
        super(new TrinketData("lucky_rock", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = LuckyRockBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.lucky_rock_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.lucky_rock_1", config.percentageOfObtaining + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float percentageOfObtaining = 5.0f;
        public List<String> itemList = Arrays.asList("minecraft:coal", "minecraft:diamond", "minecraft:iron_ingot", "minecraft:gold_ingot", "minecraft:emerald", "minecraft:redstone", "minecraft:flint", "minecraft:lapis_lazuli", "minecraft:glowstone_dust");
        public List<String> blockList = Arrays.asList("minecraft:stone", "minecraft:deepslate", "minecraft:granite", "minecraft:diorite", "minecraft:andesite", "minecraft:calcite", "minecraft:tuff");
        public boolean isEnable = true;
    }

}