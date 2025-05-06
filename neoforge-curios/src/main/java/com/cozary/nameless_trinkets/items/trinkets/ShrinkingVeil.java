package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Objects;

public class ShrinkingVeil extends TrinketItem<ShrinkingVeil.Stats> {
    public static ShrinkingVeil INSTANCE;

    public ShrinkingVeil() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = ShrinkingVeil.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.shrinking_veil_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.shrinking_veil_1", config.shrinkScalePercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.shrinking_veil_2", config.speedPercentage + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        Stats config = ShrinkingVeil.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
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
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();

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

    public static class Stats extends TrinketsStats {
        public float shrinkScalePercentage = 50.0F;
        public float speedPercentage = 110.0F;
        public boolean isEnable = true;
    }
}
