package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.AwakenedHourglassHandler;
import com.cozary.nameless_trinkets.events.ShatteredHourglassHandler;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AwakenedHourglass extends AwakenedHourglassBase implements TrinketCallback {

    public AwakenedHourglass(String id, ShatteredHourglassHandler.TemporalState state) {
        super(id, state);
    }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        AwakenedHourglassHandler.tick(entity, stack);
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        AwakenedHourglassHandler.cleanSession(entity.getUUID(), entity.level().getServer());
    }
}
