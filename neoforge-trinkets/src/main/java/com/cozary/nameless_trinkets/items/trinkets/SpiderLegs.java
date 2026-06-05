package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SpiderLegs extends SpiderLegsBase implements TrinketCallback {

    public SpiderLegs() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
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
