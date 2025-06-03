package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class Gills extends GillsBase implements Accessory {

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }



    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        return livingEntity.isEyeInFluid(FluidTags.WATER);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        Stats config = GillsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (livingEntity.getAirSupply() <= -20) {
            Vec3 vector3d = livingEntity.getDeltaMovement();
            livingEntity.setAirSupply(0);
            Random random = new Random();
            for (int i = 0; i < 8; ++i) {
                double d2 = random.nextDouble() - random.nextDouble();
                double d3 = random.nextDouble() - random.nextDouble();
                double d4 = random.nextDouble() - random.nextDouble();
                livingEntity.level().addParticle(ParticleTypes.BUBBLE, livingEntity.getX() + d2, livingEntity.getY() + d3, livingEntity.getZ() + d4, vector3d.x, vector3d.y, vector3d.z);
            }
            if (config.blindnessWhenChoking) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 10, false, false));

                livingEntity.hurt(livingEntity.damageSources().drown(), config.chokingDamage);
            }
        } else if (!livingEntity.isEyeInFluid(FluidTags.WATER) || livingEntity.level().getBlockState(new BlockPos((int) livingEntity.getX(), (int) livingEntity.getEyeY(), (int) livingEntity.getZ())).is(Blocks.BUBBLE_COLUMN)) {
            livingEntity.setAirSupply(livingEntity.getAirSupply() - config.airReductionSpeed);
        } else {
            livingEntity.setAirSupply(300);
        }
    }

}