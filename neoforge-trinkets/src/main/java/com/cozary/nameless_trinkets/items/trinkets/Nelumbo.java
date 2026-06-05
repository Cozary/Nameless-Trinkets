package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class Nelumbo extends NelumboBase implements TrinketCallback {

    public Nelumbo() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        if (entity.isEyeInFluid(FluidTags.WATER)) {
            Vec3 currentMovement = entity.getDeltaMovement();

            Vec3 newMovement = new Vec3(currentMovement.x, 0.5D, currentMovement.z);

            entity.setDeltaMovement(newMovement);
        }
    }

}
