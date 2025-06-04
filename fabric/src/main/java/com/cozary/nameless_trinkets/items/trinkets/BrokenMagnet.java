package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrokenMagnet extends BrokenMagnetBase implements Accessory {

    public BrokenMagnet(){
        super();
        AccessoriesAPI.registerAccessory(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = BrokenMagnetBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Level world = reference.entity().level();

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, reference.entity().getBoundingBox().inflate(config.range));
        for (ItemEntity item : items) {
            if (!item.isAlive())
                continue;

            if (item.getOwner() != null && item.getOwner().equals(reference.entity().getUUID()) && item.hasPickUpDelay())
                continue;

            if (!world.isClientSide) {
                item.setNoPickUpDelay();
                item.setPos(reference.entity().getX(), reference.entity().getY(), reference.entity().getZ());
            }
        }

    }
}