package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.cozary.nameless_trinkets.NamelessTrinkets.MOD_ID;

public class GravityAnchorBase extends TrinketItem<GravityAnchorBase.Stats> {
    public static GravityAnchorBase INSTANCE;
    protected final Map<String, List<String>> modifiers = new HashMap<>();

    public GravityAnchorBase() {
        super(new TrinketData("gravity_anchor", null, null, Stats.class));

        INSTANCE = this;
        initModifiers();
    }

    protected void initModifiers() {
        List<String> modifierList = new ArrayList<>();
        modifierList.add("gravity_anchor_knockback_resistance");
        modifierList.add("gravity_anchor_movement_speed");

        modifiers.put(MOD_ID, modifierList);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = GravityAnchorBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gravity_anchor_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Minecraft.getInstance().hasShiftDown()) {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gravity_anchor_1", "+" + (int)(config.knockbackResistance * 100) + "%").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gravity_anchor_2", config.damageMultiplier + "x").withStyle(ChatFormatting.GOLD));
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gravity_anchor_3", "-" + (int)(config.movementPenalty * 100) + "%").withStyle(ChatFormatting.RED));
            } else {
                tooltip.accept(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    protected void applyModifiers(LivingEntity livingEntity, Stats config) {
        for (Map.Entry<String, List<String>> entry : modifiers.entrySet()) {
            for (String key : entry.getValue()) {
                AttributeInstance attribute = getAttribute(livingEntity, key);
                if (attribute != null) {
                    ResourceLocation modifierData = ResourceLocation.fromNamespaceAndPath(entry.getKey(), key);
                    AttributeModifier modifier = createAttributeModifier(modifierData, config, key);
                    CommonUtils.applyAttributeModifier(attribute, modifier);
                }
            }
        }
    }

    protected AttributeInstance getAttribute(LivingEntity entity, String key) {
        return switch (key) {
            case "gravity_anchor_knockback_resistance" -> entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            case "gravity_anchor_movement_speed" -> entity.getAttribute(Attributes.MOVEMENT_SPEED);
            default -> null;
        };
    }

    protected AttributeModifier createAttributeModifier(ResourceLocation modifierData, Stats config, String key) {
        double value = getValueForKey(config, key);
        return new AttributeModifier(modifierData, value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    protected double getValueForKey(Stats config, String key) {
        return switch (key) {
            case "gravity_anchor_knockback_resistance" -> config.knockbackResistance;
            case "gravity_anchor_movement_speed" -> -config.movementPenalty; // Negative for penalty
            default -> 0;
        };
    }

    protected void removeModifiers(LivingEntity wearer, Stats config) {
        for (Map.Entry<String, List<String>> entry : modifiers.entrySet()) {
            for (String key : entry.getValue()) {
                AttributeInstance attribute = getAttribute(wearer, key);
                if (attribute != null) {
                    ResourceLocation modifierData = ResourceLocation.fromNamespaceAndPath(entry.getKey(), key);
                    AttributeModifier modifier = createAttributeModifier(modifierData, config, key);
                    CommonUtils.removeAttributeModifier(attribute, modifier);
                }
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float knockbackResistance = 0.85F; // +85% knockback resistance
        public float damageMultiplier = 1.5F;
        public float maxAoeRadius = 5.0F;
        public float movementPenalty = 0.15F; // -15% movement speed
        public int minFallDistance = 4;
        public boolean isEnable = true;
    }

}
