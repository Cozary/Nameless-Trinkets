package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.events.AwakenedHourglassHandler;
import com.cozary.nameless_trinkets.events.ShatteredHourglassHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AwakenedHourglass extends AwakenedHourglassBase implements ICurioItem {

    public AwakenedHourglass(String id, ShatteredHourglassHandler.TemporalState state) {
        super(id, state);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        AwakenedHourglassHandler.tick(slotContext.entity(), stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        AwakenedHourglassHandler.cleanSession(slotContext.entity().getUUID(), slotContext.entity().level().getServer());
    }
}
