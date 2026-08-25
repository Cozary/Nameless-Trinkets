package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.EclipseAshesHandler;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EclipseAshes extends EclipseAshesBase implements TrinketCallback {

    public EclipseAshes() {
        super();
    }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        EclipseAshesHandler.tick(entity, stack);
    }
}
