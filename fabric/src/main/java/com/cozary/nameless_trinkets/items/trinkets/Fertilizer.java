package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public class Fertilizer extends FertilizerBase implements Trinket {

    public Fertilizer() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        commonTick(stack, entity);
    }

}
