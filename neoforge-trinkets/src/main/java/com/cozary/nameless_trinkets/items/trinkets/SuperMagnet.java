package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SuperMagnet extends SuperMagnetBase implements TrinketCallback {

    public SuperMagnet() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        LivingEntity livingEntity = entity;
        Stats config = SuperMagnetBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Level world = livingEntity.level();

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, livingEntity.getBoundingBox().inflate(config.range));
        for (ItemEntity item : items) {
            if (!item.isAlive())
                continue;

            if (item.getOwner() != null && item.getOwner().equals(livingEntity.getUUID()) && item.hasPickUpDelay())
                continue;

            if (!world.isClientSide()) {
                item.setNoPickUpDelay();
                item.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            }
        }

        List<ExperienceOrb> xporbs = world.getEntitiesOfClass(ExperienceOrb.class, livingEntity.getBoundingBox().inflate(config.range));
        for (ExperienceOrb orb : xporbs) {
            if (!world.isClientSide()) {
                orb.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            }

        }

    }


}
