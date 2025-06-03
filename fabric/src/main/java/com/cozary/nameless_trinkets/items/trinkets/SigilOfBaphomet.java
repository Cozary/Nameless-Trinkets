package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public class SigilOfBaphomet extends SigilOfBaphometBase implements Accessory {

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = SigilOfBaphometBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof ServerPlayer))
            return;

        if (!stack.isEmpty() && stack.getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0) {

            stack.set(ModDataComponents.SIGIL_COUNT.get(), stack.getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) - 1);

        }
    }

}