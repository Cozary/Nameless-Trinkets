package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Fertilizer extends FertilizerBase implements TrinketCallback {

    public Fertilizer() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        commonTick(stack, entity);
    }

}
