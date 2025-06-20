package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public class ExperienceBattery extends ExperienceBatteryBase implements Accessory {

    public ExperienceBattery() {
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

}