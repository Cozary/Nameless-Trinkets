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

import java.util.List;

public class ScarabAmuletBase extends TrinketItem<ScarabAmuletBase.Stats> {
    public static ScarabAmuletBase INSTANCE;

    public ScarabAmuletBase() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = ScarabAmuletBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_1", config.speedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_2").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_3").withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.add(Component.translatable(ChatFormatting.GRAY + "Suggested By: AzrouStone"));
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