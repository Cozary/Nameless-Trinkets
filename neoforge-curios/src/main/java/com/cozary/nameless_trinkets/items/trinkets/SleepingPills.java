package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;

public class SleepingPills extends SleepingPillsBase implements ICurioItem {

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
        Stats config = SleepingPillsBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();

        if (!livingEntity.isSpectator()) {

            if (!stack.isEmpty()) {
                if (!livingEntity.hasEffect(MobEffects.NIGHT_VISION) || Objects.requireNonNull(livingEntity.getEffect(MobEffects.NIGHT_VISION)).getDuration() < 600)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, config.nightVisionTime, 0, false, false));

                if (!livingEntity.hasEffect(MobEffects.WEAKNESS) || Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getDuration() < 600)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, config.weaknessTime, 0, false, false));

                List<Phantom> list = livingEntity.level().getEntitiesOfClass(Phantom.class, livingEntity.getBoundingBox().inflate(config.phantomRange), EntitySelector.ENTITY_STILL_ALIVE);

                if (!list.isEmpty()) {
                    for (Phantom phantomEntity : list) {
                        if (!phantomEntity.level().isClientSide) {

                            Vec3 vector3d = phantomEntity.getDeltaMovement();
                            ((ServerLevel) phantomEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.ASH, phantomEntity.getX(), phantomEntity.getY(), phantomEntity.getZ(), 250, vector3d.x, 0.3D, vector3d.z, 5);
                        }

                        phantomEntity.discard();
                    }
                }
            }
        }
    }


}