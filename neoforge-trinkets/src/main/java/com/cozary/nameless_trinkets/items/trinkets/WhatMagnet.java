package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class WhatMagnet extends WhatMagnetBase implements TrinketCallback {

    public WhatMagnet() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        LivingEntity livingEntity = entity;
        Stats config = WhatMagnetBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        Level world = livingEntity.level();
        Random random = new Random();

        float rx = random.nextFloat() * 5F - 2.5F;
        float rz = random.nextFloat() * 5F - 2.5F;
        List<LivingEntity> entitiesOfClass = world.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(config.range));
        for (LivingEntity targetEntity : entitiesOfClass) {
            if (!world.isClientSide()) {
                targetEntity.setPos(livingEntity.getX() + rx, livingEntity.getY(), livingEntity.getZ() + rz);
            }
        }

    }

}
