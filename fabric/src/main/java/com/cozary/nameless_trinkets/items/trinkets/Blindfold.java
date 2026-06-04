package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public class Blindfold extends BlindfoldBase implements Trinket {

    public Blindfold() {
        super();
        }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = BlindfoldBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        entity.setInvisible(true);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = BlindfoldBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        entity.setInvisible(false);
    }

    

    

}