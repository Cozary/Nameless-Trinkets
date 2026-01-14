package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SuperMagnet extends SuperMagnetBase implements Accessory {

    public SuperMagnet() {
        super();
        AccessoryRegistry.register(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
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