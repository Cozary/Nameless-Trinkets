package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Random;

public class WhatMagnet extends WhatMagnetBase implements ICurioItem {

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
            if (!world.isClientSide()) {
                entity.setPos(livingEntity.getX() + rx, livingEntity.getY(), livingEntity.getZ() + rz);
            }
        }

    }

}
