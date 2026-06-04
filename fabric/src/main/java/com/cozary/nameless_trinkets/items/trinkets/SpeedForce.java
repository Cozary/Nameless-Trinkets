package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class SpeedForce extends SpeedForceBase implements Accessory {

    public SpeedForce() {
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
        Stats config = SpeedForceBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();
        if (livingEntity.level().isClientSide())
            return;


        if (!livingEntity.level().isClientSide() && !stack.isEmpty() && config.speedForceParticles) {

            Vec3 vector3d = livingEntity.getDeltaMovement();
            ((ServerLevel) livingEntity.level()).sendParticles(ParticleTypes.CLOUD, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, vector3d.x * -4.0D, 0.3D, vector3d.z * -4.0D, 0.1);
        }

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force_movement_speed"),
                config.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        CommonUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(reference.entity().getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force_movement_speed"),
                        trinketConfig.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }


}