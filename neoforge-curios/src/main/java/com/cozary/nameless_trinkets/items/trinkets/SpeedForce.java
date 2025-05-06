package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.EntityUtils;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Objects;

public class SpeedForce extends SpeedForceBase implements ICurioItem {

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
        Stats config = SpeedForceBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();


        if (!livingEntity.level().isClientSide && !stack.isEmpty() && config.speedForceParticles) {

            Vec3 vector3d = livingEntity.getDeltaMovement();
            ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.CLOUD, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, vector3d.x * -4.0D, 0.3D, vector3d.z * -4.0D, 0.1);
        }

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force_movement_speed"),
                config.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        EntityUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force_movement_speed"),
                        trinketConfig.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }


}