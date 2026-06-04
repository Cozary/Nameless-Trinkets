package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Objects;

public class ShrinkingVeil extends ShrinkingVeilBase implements ICurioItem {

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        Stats config = ShrinkingVeilBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.level();

        if (world.isClientSide())
            return;

        AttributeInstance attribScale = livingEntity.getAttribute(Attributes.SCALE);
        AttributeModifier scaleModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_scale"),
                -config.shrinkScalePercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribScale != null;
        CommonUtils.applyAttributeModifier(attribScale, scaleModifier);

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_speed"),
                trinketConfig.speedPercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        CommonUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();

        CommonUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.SCALE)),
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_scale"),
                        -trinketConfig.shrinkScalePercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        CommonUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "shrinking_veil_speed"),
                        trinketConfig.speedPercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}
