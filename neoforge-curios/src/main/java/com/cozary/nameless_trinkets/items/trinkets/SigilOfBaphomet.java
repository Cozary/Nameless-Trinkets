package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class SigilOfBaphomet extends SigilOfBaphometBase implements ICurioItem {

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
        Stats config = SigilOfBaphometBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(slotContext.entity() instanceof ServerPlayer))
            return;

        if (!stack.isEmpty() && stack.getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0) {

            stack.set(ModDataComponents.SIGIL_COUNT.get(), stack.getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) - 1);

        }
    }

}