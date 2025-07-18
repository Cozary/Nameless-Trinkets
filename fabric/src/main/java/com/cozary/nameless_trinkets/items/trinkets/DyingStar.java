package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DyingStar extends DyingStarBase implements Accessory {

    public DyingStar() {
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
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = DyingStarBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        for (int i = 0; i < AttributeSelector.values().length; i++) {

            AttributeSelector attributeSelector = AttributeSelector.values()[i];


            float attributeIncrement = stack.getOrDefault(attributeSelector.getDataComponentType(), 0.0f);

            if (attributeIncrement > 0) {

                AttributeInstance attributeDamage = livingEntity.getAttribute(attributeSelector.getAttributeHolder());
                AttributeModifier attributeModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dying_star_" + attributeSelector.getAttributeHolder().getRegisteredName().replace(".", "_").replace(":", "_")),
                        attributeIncrement, AttributeModifier.Operation.ADD_VALUE);

                if (attributeDamage != null && attributeModifier != null) {
                    CommonUtils.applyAttributeModifier(attributeDamage, attributeModifier);
                }
            }
        }

    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {

        for (int i = 0; i < AttributeSelector.values().length; i++) {

            AttributeSelector attributeSelector = AttributeSelector.values()[i];

            float attributeIncrement = stack.getOrDefault(attributeSelector.getDataComponentType(), 0.0f);
            if (attributeIncrement > 0) {

                AttributeInstance attributeDamage = reference.entity().getAttribute(attributeSelector.getAttributeHolder());
                AttributeModifier attributeModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dying_star_" + attributeSelector.getAttributeHolder().getRegisteredName().replace(".", "_").replace(":", "_")),
                        attributeIncrement, AttributeModifier.Operation.ADD_VALUE);

                if (attributeDamage != null && attributeModifier != null) {
                    CommonUtils.removeAttributeModifier(attributeDamage, attributeModifier);
                }
            }
        }
    }

}