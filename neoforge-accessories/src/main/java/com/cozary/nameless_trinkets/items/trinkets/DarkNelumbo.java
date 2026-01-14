package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class DarkNelumbo extends DarkNelumboBase implements Accessory {

    public DarkNelumbo() {
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
        if (reference.entity().isEyeInFluid(FluidTags.LAVA)) {
            Vec3 currentMovement = reference.entity().getDeltaMovement();

            Vec3 newMovement = new Vec3(currentMovement.x, 0.5D, currentMovement.z);

            reference.entity().setDeltaMovement(newMovement);
        }
    }

}
