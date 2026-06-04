package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrokenMagnet extends BrokenMagnetBase implements Trinket {

    public BrokenMagnet() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = BrokenMagnetBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Level world = entity.level();

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(config.range));
        for (ItemEntity item : items) {
            if (!item.isAlive())
                continue;

            if (item.getOwner() != null && item.getOwner().equals(entity.getUUID()) && item.hasPickUpDelay())
                continue;

            if (!world.isClientSide()) {
                item.setNoPickUpDelay();
                item.setPos(entity.getX(), entity.getY(), entity.getZ());
            }
        }

    }
}