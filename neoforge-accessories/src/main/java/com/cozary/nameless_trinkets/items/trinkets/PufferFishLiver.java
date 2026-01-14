package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

public class PufferFishLiver extends PufferFishLiverBase implements Accessory {

    public PufferFishLiver() {
        super();
        AccessoryRegistry.register(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!stack.isEmpty()) {
            if (reference.entity().hasEffect(MobEffects.POISON)) {

                reference.entity().removeEffect(MobEffects.POISON);
            }
        }

    }

}