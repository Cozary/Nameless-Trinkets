package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Nelumbo extends NelumboBase implements ICurioItem {

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
        if (slotContext.entity().isEyeInFluid(FluidTags.WATER)) {
            Vec3 currentMovement = slotContext.entity().getDeltaMovement();

            Vec3 newMovement = new Vec3(currentMovement.x, 0.5D, currentMovement.z);

            slotContext.entity().setDeltaMovement(newMovement);
        }
    }

}
