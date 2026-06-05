package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Blindfold extends BlindfoldBase implements TrinketCallback {

    public Blindfold() {
        super();
    }

    @Override
    public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        Stats config = BlindfoldBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        entity.setInvisible(true);
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        Stats config = BlindfoldBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        entity.setInvisible(false);
    }


}
