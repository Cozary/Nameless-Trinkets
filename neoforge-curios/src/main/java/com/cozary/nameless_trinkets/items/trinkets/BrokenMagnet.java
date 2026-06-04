package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BrokenMagnet extends BrokenMagnetBase implements ICurioItem {

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
        Stats config = BrokenMagnetBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Level world = slotContext.entity().level();

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, slotContext.entity().getBoundingBox().inflate(config.range));
        for (ItemEntity item : items) {
            if (!item.isAlive())
                continue;

            if (item.getOwner() != null && item.getOwner().equals(slotContext.entity().getUUID()) && item.hasPickUpDelay())
                continue;

            if (!world.isClientSide()) {
                item.setNoPickUpDelay();
                item.setPos(slotContext.entity().getX(), slotContext.entity().getY(), slotContext.entity().getZ());
            }
        }

    }
}
