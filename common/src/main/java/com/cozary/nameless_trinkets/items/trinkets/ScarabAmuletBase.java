package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;
public class ScarabAmuletBase extends TrinketItem<ScarabAmuletBase.Stats> {
    public static ScarabAmuletBase INSTANCE;

    public ScarabAmuletBase() {
        super(new TrinketData("scarab_amulet", null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = ScarabAmuletBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_1", config.speedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_2").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_3").withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.accept(Component.translatable(ChatFormatting.GRAY + "Suggested By: AzrouStone"));
            }
        }
    }

    protected BlockPos getBlockPosBelowThatAffectsMyMovement(LivingEntity player) {
        return new BlockPos(player.getBlockX(), player.getBlockY() - 1, player.getBlockZ());
    }

    public static class Stats extends TrinketsStats {
        public float speedMultiplierPercentage = 110.0f;
        public boolean isEnable = true;

    }

}