package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class PufferFishLiver extends TrinketItemCurios<PufferFishLiverBase.Stats> {
    public static PufferFishLiverBase INSTANCE;

    public PufferFishLiver() {
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
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!stack.isEmpty()) {
            if (slotContext.entity().hasEffect(MobEffects.POISON)) {

                slotContext.entity().removeEffect(MobEffects.POISON);
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.puffer_fish_liver_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.puffer_fish_liver_1", config.chanceToApplyPoison + "%", config.poisonLevel + 1, config.poisonTime / 20).withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.puffer_fish_liver_2").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float chanceToApplyPoison = 30.0f;
        public int poisonTime = 100;
        public int poisonLevel = 1;
        public boolean isEnable = true;

    }

}