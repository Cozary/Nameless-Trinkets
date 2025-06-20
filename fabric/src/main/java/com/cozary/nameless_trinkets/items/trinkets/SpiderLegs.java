package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SpiderLegs extends SpiderLegsBase implements Accessory {

    public SpiderLegs() {
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
        Stats config = SpiderLegsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();

        if (!livingEntity.isSpectator()) {

            if (!stack.isEmpty()) {
                if (livingEntity.horizontalCollision) {

                    Vec3 motion = livingEntity.getDeltaMovement();
                    livingEntity.setDeltaMovement(motion.x, config.climbSpeed, motion.z);
                }
            }
        }
    }

}