package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class PufferFishLiver extends PufferFishLiverBase implements ICurioItem {

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
        Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!stack.isEmpty()) {
            if (slotContext.entity().hasEffect(MobEffects.POISON)) {

                slotContext.entity().removeEffect(MobEffects.POISON);
            }
        }

    }

}