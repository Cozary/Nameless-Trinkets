package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Random;

public class WhatMagnet extends TrinketItemCurios<WhatMagnetBase.Stats> {
    public static WhatMagnetBase INSTANCE;

    public WhatMagnet() {
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
        LivingEntity livingEntity = slotContext.entity();
        Stats config = WhatMagnetBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        Level world = livingEntity.level();
        Random random = new Random();

        float rx = random.nextFloat() * 5F - 2.5F;
        float rz = random.nextFloat() * 5F - 2.5F;
        List<LivingEntity> entitiesOfClass = world.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(config.range));
        for (LivingEntity entity : entitiesOfClass) {
            if (!world.isClientSide) {
                entity.setPos(livingEntity.getX() + rx, livingEntity.getY(), livingEntity.getZ() + rz);
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = WhatMagnetBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.what_magnet_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.what_magnet_1", config.range).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float range = 25.0F;
        public boolean isEnable = true;

    }

}