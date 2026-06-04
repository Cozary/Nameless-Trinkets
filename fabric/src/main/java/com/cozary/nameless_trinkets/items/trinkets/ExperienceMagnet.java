package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ExperienceMagnet extends ExperienceMagnetBase implements Trinket {

    public ExperienceMagnet() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = ExperienceMagnetBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            return;
        }

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

        if (world.isClientSide()) {
            return;
        }

        List<ExperienceOrb> xporbs = world.getEntitiesOfClass(ExperienceOrb.class, livingEntity.getBoundingBox().inflate(config.range));
        for (ExperienceOrb orb : xporbs) {
            orb.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        }
    }

}