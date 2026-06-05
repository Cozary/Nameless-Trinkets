package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class PufferFishLiver extends PufferFishLiverBase implements TrinketCallback {

    public PufferFishLiver() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!stack.isEmpty()) {
            if (entity.hasEffect(MobEffects.POISON)) {

                entity.removeEffect(MobEffects.POISON);
            }
        }

    }

}
