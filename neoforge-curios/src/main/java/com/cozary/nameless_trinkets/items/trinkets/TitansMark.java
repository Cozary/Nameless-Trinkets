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

public class TitansMark extends TitansMarkBase implements ICurioItem {

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
        Stats config = TitansMarkBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        AttributeInstance attribScale = livingEntity.getAttribute(Attributes.SCALE);
        AttributeModifier scaleModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_scale"),
                config.extraScalePercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribScale != null;
        CommonUtils.applyAttributeModifier(attribScale, scaleModifier);

        AttributeInstance attribAttackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier attackDamageModifier = new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_attack_damage"),
                config.attackDamagePercentage / 100,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribAttackDamage != null;
        CommonUtils.applyAttributeModifier(attribAttackDamage, attackDamageModifier);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();

        CommonUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.SCALE)),
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_scale"),
                        trinketConfig.extraScalePercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        CommonUtils.removeAttributeModifier(Objects.requireNonNull(livingEntity.getAttribute(Attributes.ATTACK_DAMAGE)),
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "titans_mark_attack_damage"),
                        trinketConfig.attackDamagePercentage / 100,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}
