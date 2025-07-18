package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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

public class GodsCrownBase extends TrinketItem<GodsCrownBase.Stats> {
    public static GodsCrownBase INSTANCE;

    protected final Map<String, List<String>> modifiers = new HashMap<>();

    public GodsCrownBase() {
        super(new TrinketData("gods_crown", null, null, Stats.class));

        INSTANCE = this;

        initModifiers();
    }


    protected void initModifiers() {
        List<String> modifierList = new ArrayList<>();
        modifierList.add("gods_crown_max_health");
        modifierList.add("gods_crown_movement_speed");
        modifierList.add("gods_crown_flying_speed");
        modifierList.add("gods_crown_attack_damage");
        modifierList.add("gods_crown_armor");
        modifierList.add("gods_crown_attack_speed");
        modifierList.add("gods_crown_armor_thougness");
        modifierList.add("gods_crown_attack_knockback");
        modifierList.add("gods_crown_knockback_resistance");
        modifierList.add("gods_crown_luck");
        modifierList.add("gods_crown_block_reach");
        modifierList.add("gods_crown_entity_reach");
        modifierList.add("gods_crown_step_height_addition");
        modifierList.add("gods_crown_swim_speed");

        modifiers.put(MOD_ID, modifierList);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        Stats config = GodsCrownBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.accept(Component.translatable("tooltip.nameless_trinkets.gods_crown_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));

            if (Screen.hasShiftDown()) {
                Map<String, Double> stats = new HashMap<>();
                stats.put("Max Health", config.percentageExtraMaxHealth);
                stats.put("Movement Speed", config.percentageExtraSpeed);
                stats.put("Flying Speed", config.percentageExtraFlySpeed);
                stats.put("Attack Damage", config.percentageExtraAttack);
                stats.put("Armor", config.percentageExtraArmor);
                stats.put("Attack Speed", config.percentageExtraAttackSpeed);
                stats.put("Armor Toughness", config.percentageExtraArmorToughness);
                stats.put("Attack Knockback", config.percentageExtraAttackKnockback);
                stats.put("Knockback Resistance", config.percentageExtraKnockbackResistance);
                stats.put("Luck", config.percentageExtraLuck);
                stats.put("Swim Speed", config.percentageExtraSwimSpeed);
                stats.put("Block Reach", config.percentageExtraBlockReach);
                stats.put("Entity Reach", config.percentageExtraEntityReach);
                stats.put("Step Height Addition", config.percentageExtraStepHeightAddition);

                for (Map.Entry<String, Double> entry : stats.entrySet()) {
                    if (entry.getValue() > 0) {
                        tooltip.accept(Component.translatable(entry.getKey()).append(" +" + entry.getValue() + "%").withStyle(ChatFormatting.GOLD));
                    }
                }

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
            case "gods_crown_max_health" -> entity.getAttribute(Attributes.MAX_HEALTH);
            case "gods_crown_movement_speed" -> entity.getAttribute(Attributes.MOVEMENT_SPEED);
            case "gods_crown_flying_speed" -> entity.getAttribute(Attributes.FLYING_SPEED);
            case "gods_crown_attack_damage" -> entity.getAttribute(Attributes.ATTACK_DAMAGE);
            case "gods_crown_armor" -> entity.getAttribute(Attributes.ARMOR);
            case "gods_crown_attack_speed" -> entity.getAttribute(Attributes.ATTACK_SPEED);
            case "gods_crown_armor_thougness" -> entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
            case "gods_crown_attack_knockback" -> entity.getAttribute(Attributes.ATTACK_KNOCKBACK);
            case "gods_crown_knockback_resistance" -> entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            case "gods_crown_luck" -> entity.getAttribute(Attributes.LUCK);
            case "gods_crown_swim_speed" -> entity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
            case "gods_crown_block_reach" -> entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            case "gods_crown_entity_reach" -> entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            case "gods_crown_step_height_addition" -> entity.getAttribute(Attributes.STEP_HEIGHT);
            default -> null;
        };
    }

    protected AttributeModifier createAttributeModifier(ResourceLocation modifierData, Stats config, String key) {
        double percentage = getPercentageForKey(config, key);
        return new AttributeModifier(modifierData,
                percentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    protected double getPercentageForKey(Stats config, String key) {
        return switch (key) {
            case "gods_crown_max_health" -> config.percentageExtraMaxHealth;
            case "gods_crown_movement_speed" -> config.percentageExtraSpeed;
            case "gods_crown_flying_speed" -> config.percentageExtraFlySpeed;
            case "gods_crown_attack_damage" -> config.percentageExtraAttack;
            case "gods_crown_armor" -> config.percentageExtraArmor;
            case "gods_crown_attack_speed" -> config.percentageExtraAttackSpeed;
            case "gods_crown_armor_thougness" -> config.percentageExtraArmorToughness;
            case "gods_crown_attack_knockback" -> config.percentageExtraAttackKnockback;
            case "gods_crown_knockback_resistance" -> config.percentageExtraKnockbackResistance;
            case "gods_crown_luck" -> config.percentageExtraLuck;
            case "gods_crown_swim_speed" -> config.percentageExtraSwimSpeed;
            case "gods_crown_block_reach" -> config.percentageExtraBlockReach;
            case "gods_crown_entity_reach" -> config.percentageExtraEntityReach;
            case "gods_crown_step_height_addition" -> config.percentageExtraStepHeightAddition;
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
        public double percentageExtraMaxHealth = 50.0f;
        public double percentageExtraSpeed = 25.0f;
        public double percentageExtraFlySpeed = 15.0f;
        public double percentageExtraAttack = 40.0f;
        public double percentageExtraArmor = 30.0f;
        public double percentageExtraAttackSpeed = 25.0f;
        public double percentageExtraArmorToughness = 20.0f;
        public double percentageExtraAttackKnockback = 15.0f;
        public double percentageExtraKnockbackResistance = 30.0f;
        public double percentageExtraLuck = 50.0f;
        public double percentageExtraSwimSpeed = 25.0f;
        public double percentageExtraBlockReach = 25.0f;
        public double percentageExtraEntityReach = 25.0f;
        public double percentageExtraStepHeightAddition = 0.0f;

        public boolean isEnable = true;
    }


}