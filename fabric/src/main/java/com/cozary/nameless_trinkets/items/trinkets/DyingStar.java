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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DyingStar extends DyingStarBase implements Trinket {

    public DyingStar() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = DyingStarBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

        if (world.isClientSide())
            return;

        for (int i = 0; i < AttributeSelector.values().length; i++) {

            AttributeSelector attributeSelector = AttributeSelector.values()[i];


            float attributeIncrement = stack.getOrDefault(attributeSelector.getDataComponentType(), 0.0f);

            if (attributeIncrement > 0) {

                AttributeInstance attributeDamage = livingEntity.getAttribute(attributeSelector.getAttributeHolder());
                AttributeModifier attributeModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dying_star_" + attributeSelector.getAttributeHolder().getRegisteredName().replace(".", "_").replace(":", "_")),
                        attributeIncrement, AttributeModifier.Operation.ADD_VALUE);

                if (attributeDamage != null && attributeModifier != null) {
                    CommonUtils.applyAttributeModifier(attributeDamage, attributeModifier);
                }
            }
        }

    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {

        for (int i = 0; i < AttributeSelector.values().length; i++) {

            AttributeSelector attributeSelector = AttributeSelector.values()[i];

            float attributeIncrement = stack.getOrDefault(attributeSelector.getDataComponentType(), 0.0f);
            if (attributeIncrement > 0) {

                AttributeInstance attributeDamage = entity.getAttribute(attributeSelector.getAttributeHolder());
                AttributeModifier attributeModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dying_star_" + attributeSelector.getAttributeHolder().getRegisteredName().replace(".", "_").replace(":", "_")),
                        attributeIncrement, AttributeModifier.Operation.ADD_VALUE);

                if (attributeDamage != null && attributeModifier != null) {
                    CommonUtils.removeAttributeModifier(attributeDamage, attributeModifier);
                }
            }
        }
    }

}