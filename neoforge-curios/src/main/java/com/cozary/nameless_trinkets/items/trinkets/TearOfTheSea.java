package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Objects;

public class TearOfTheSea extends TearOfTheSeaBase implements ICurioItem {

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
        Stats config = TearOfTheSeaBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();


        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "tear_of_the_sea_swim_speed"),
                config.swimSpeedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_VALUE);

        assert attribSpeed != null;
        EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);

    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        EntityUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "tear_of_the_sea_swim_speed"),
                        trinketConfig.swimSpeedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_VALUE));
    }

}