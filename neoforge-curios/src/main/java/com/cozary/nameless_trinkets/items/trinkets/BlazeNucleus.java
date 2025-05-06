package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BlazeNucleus extends TrinketItemCurios<BlazeNucleusBase.Stats> {
    public static BlazeNucleusBase INSTANCE;

    public BlazeNucleus() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = BlazeNucleusBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                if (config.fireDamageReductionPercentage == 100) {
                    tooltip.add(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_1").withStyle(ChatFormatting.GOLD));
                } else {
                    tooltip.add(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_2", config.fireDamageReductionPercentage + "%").withStyle(ChatFormatting.GOLD));
                }
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.blaze_nucleus_3").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int setEnemyInFireTicks = 300;
        public float fireDamageReductionPercentage = 80.0F;
        public boolean isEnable = true;

    }

}