package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SpiderLegs extends SpiderLegsBase implements Trinket {

    public SpiderLegs() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = SpiderLegsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;

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