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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Objects;

public class TearOfTheSea extends TrinketItem<TearOfTheSea.Stats> {
    public static TearOfTheSea INSTANCE;

    public TearOfTheSea() {
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
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = TearOfTheSea.INSTANCE.getTrinketConfig();
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = TearOfTheSea.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.tear_of_the_sea_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.tear_of_the_sea_1", config.swimSpeedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.add(Component.translatable(ChatFormatting.GRAY + "Suggested By: JayOnline_"));
            }
        }
    }


    public static class Stats extends TrinketsStats {
        public double swimSpeedMultiplierPercentage = 50.0f;
        public boolean isEnable = true;

    }
}