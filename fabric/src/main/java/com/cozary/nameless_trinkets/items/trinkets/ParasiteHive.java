package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.ParasiteHiveHandler;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ParasiteHive extends ParasiteHiveBase implements TrinketCallback {

    public ParasiteHive() {
        super();
    }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        ParasiteHiveHandler.tick(entity);
    }
}
