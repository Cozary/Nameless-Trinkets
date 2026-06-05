package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class GhastEye extends GhastEyeBase implements TrinketCallback {

    public GhastEye() {
        super();
    }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        Stats config = GhastEyeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

        if (world.isClientSide())
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier healthModifier = new AttributeModifier(
                Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "ghast_eye_extra_health"),
                config.extraHearts,
                AttributeModifier.Operation.ADD_VALUE);

        assert attribSpeed != null;
        CommonUtils.applyAttributeModifier(attribSpeed, healthModifier);
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)),
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "ghast_eye_extra_health"),
                        trinketConfig.extraHearts,
                        AttributeModifier.Operation.ADD_VALUE));
    }


}
