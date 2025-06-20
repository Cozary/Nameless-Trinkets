package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ShrinkingVeil extends ShrinkingVeilBase implements Accessory {

    public ShrinkingVeil() {
        super();
        AccessoriesAPI.registerAccessory(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = ShrinkingVeilBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        AttributeInstance attribScale = livingEntity.getAttribute(Attributes.SCALE);
        AttributeModifier scaleModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_scale"),
                -config.shrinkScalePercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribScale != null;
        EntityUtils.applyAttributeModifier(attribScale, scaleModifier);

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_speed"),
                trinketConfig.speedPercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }


    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();

        EntityUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.SCALE)),
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_scale"),
                        -trinketConfig.shrinkScalePercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        EntityUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_speed"),
                        trinketConfig.speedPercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}
