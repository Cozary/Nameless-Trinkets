package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.ShatteredHourglassHandler;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ShatteredHourglass extends ShatteredHourglassBase implements TrinketCallback {

    public ShatteredHourglass() {
        super();
    }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        ShatteredHourglassHandler.tick(entity, stack);
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        ShatteredHourglassHandler.cleanSession(entity.getUUID(), entity.level().getServer());
    }
}
