package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class ExperienceMagnet extends ExperienceMagnetBase implements ICurioItem {


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
        Stats config = ExperienceMagnetBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            return;
        }

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.level();

        if (world.isClientSide) {
            return;
        }

        List<ExperienceOrb> xporbs = world.getEntitiesOfClass(ExperienceOrb.class, livingEntity.getBoundingBox().inflate(config.range));
        for (ExperienceOrb orb : xporbs) {
            orb.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        }
    }

}