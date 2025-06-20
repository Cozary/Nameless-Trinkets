package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class Nelumbo extends NelumboBase implements Accessory {

    public Nelumbo() {
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
        if (reference.entity().isEyeInFluid(FluidTags.WATER)) {
            Vec3 currentMovement = reference.entity().getDeltaMovement();

            Vec3 newMovement = new Vec3(currentMovement.x, 0.5D, currentMovement.z);

            reference.entity().setDeltaMovement(newMovement);
        }
    }

}
