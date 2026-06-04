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
import net.minecraft.world.level.Level;

import java.util.Objects;

public class TitansMark extends TitansMarkBase implements Trinket {

    public TitansMark() {
        super();
        }

    

    

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = TitansMarkBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

        if (world.isClientSide())
            return;

        AttributeInstance attribScale = livingEntity.getAttribute(Attributes.SCALE);
        AttributeModifier scaleModifier = new AttributeModifier(
                Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_scale"),
                config.extraScalePercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribScale != null;
        CommonUtils.applyAttributeModifier(attribScale, scaleModifier);

        AttributeInstance attribAttackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier attackDamageModifier = new AttributeModifier(
                Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_attack_damage"),
                config.attackDamagePercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribAttackDamage != null;
        CommonUtils.applyAttributeModifier(attribAttackDamage, attackDamageModifier);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        LivingEntity livingEntity = entity;

        CommonUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.SCALE)),
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_scale"),
                        trinketConfig.extraScalePercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        CommonUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.ATTACK_DAMAGE)),
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_attack_damage"),
                        trinketConfig.attackDamagePercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}
