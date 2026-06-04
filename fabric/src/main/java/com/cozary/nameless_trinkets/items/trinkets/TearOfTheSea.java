package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class TearOfTheSea extends TearOfTheSeaBase implements Trinket {

    public TearOfTheSea() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = TearOfTheSeaBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
        if (livingEntity.level().isClientSide())
            return;


        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        AttributeModifier speedModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "tear_of_the_sea_swim_speed"),
                config.swimSpeedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_VALUE);

        assert attribSpeed != null;
        CommonUtils.applyAttributeModifier(attribSpeed, speedModifier);

    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(entity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY)),
                new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "tear_of_the_sea_swim_speed"),
                        trinketConfig.swimSpeedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_VALUE));
    }

}