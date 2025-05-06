package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class SpiderLegs extends SpiderLegsBase implements ICurioItem {


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
        Stats config = SpiderLegsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();

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