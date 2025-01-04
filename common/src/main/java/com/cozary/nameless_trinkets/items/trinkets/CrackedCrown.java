package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import io.wispforest.accessories.api.slot.SlotReference;
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
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cozary.nameless_trinkets.NamelessTrinkets.MOD_ID;

public class CrackedCrown extends TrinketItem<CrackedCrown.Stats> {

    public static CrackedCrown INSTANCE;
    private final Map<String, List<String>> modifiers = new HashMap<>();

    public CrackedCrown() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;

        initModifiers();
    }

    private void initModifiers() {
        List<String> modifierList = new ArrayList<>();
        modifierList.add("cracked_crown_max_health");
        modifierList.add("cracked_crown_movement_speed");
        modifierList.add("cracked_crown_flying_speed");
        modifierList.add("cracked_crown_attack_damage");
        modifierList.add("cracked_crown_armor");
        modifierList.add("cracked_crown_attack_speed");
        modifierList.add("cracked_crown_armor_thougness");
        modifierList.add("cracked_crown_attack_knockback");
        modifierList.add("cracked_crown_knockback_resistance");
        modifierList.add("cracked_crown_luck");
        modifierList.add("cracked_crown_block_reach");
        modifierList.add("cracked_crown_entity_reach");
        modifierList.add("cracked_crown_step_height_addition");
        modifierList.add("cracked_crown_swim_speed");

        modifiers.put(MOD_ID, modifierList);
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
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
            return;
        }

        tooltip.add(Component.translatable("tooltip.nameless_trinkets.cracked_crown_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        if (Screen.hasShiftDown()) {
            double average = (config.percentageExtraArmor + config.percentageExtraArmorToughness + config.percentageExtraAttack + config.percentageExtraAttackKnockback + config.percentageExtraAttackSpeed + config.percentageExtraKnockbackResistance) / 6;
            String translationKey = average == 50 ? "tooltip.nameless_trinkets.cracked_crown_1" : "tooltip.nameless_trinkets.cracked_crown_2";
            tooltip.add(Component.translatable(translationKey).withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide()) return;

        applyModifiers(livingEntity, config);
    }

    private void applyModifiers(LivingEntity livingEntity, Stats config) {
        for (Map.Entry<String, List<String>> entry : modifiers.entrySet()) {
            for (String key : entry.getValue()) {
                AttributeInstance attribute = getAttribute(livingEntity, key);
                if (attribute != null) {
                    ResourceLocation modifierData = ResourceLocation.fromNamespaceAndPath(entry.getKey(), key);
                    AttributeModifier modifier = createAttributeModifier(modifierData, config, key);
                    EntityUtils.applyAttributeModifier(attribute, modifier);
                }
            }
        }
    }

    private AttributeInstance getAttribute(LivingEntity entity, String key) {
        return switch (key) {
            case "cracked_crown_max_health" -> entity.getAttribute(Attributes.MAX_HEALTH);
            case "cracked_crown_movement_speed" -> entity.getAttribute(Attributes.MOVEMENT_SPEED);
            case "cracked_crown_flying_speed" -> entity.getAttribute(Attributes.FLYING_SPEED);
            case "cracked_crown_attack_damage" -> entity.getAttribute(Attributes.ATTACK_DAMAGE);
            case "cracked_crown_armor" -> entity.getAttribute(Attributes.ARMOR);
            case "cracked_crown_attack_speed" -> entity.getAttribute(Attributes.ATTACK_SPEED);
            case "cracked_crown_armor_thougness" -> entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
            case "cracked_crown_attack_knockback" -> entity.getAttribute(Attributes.ATTACK_KNOCKBACK);
            case "cracked_crown_knockback_resistance" -> entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            case "cracked_crown_luck" -> entity.getAttribute(Attributes.LUCK);
            case "cracked_crown_block_reach" -> entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            case "cracked_crown_entity_reach" -> entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            case "cracked_crown_step_height_addition" -> entity.getAttribute(Attributes.STEP_HEIGHT);
            case "cracked_crown_swim_speed" -> entity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
            default -> null;
        };
    }

    private AttributeModifier createAttributeModifier(ResourceLocation modifierData, Stats config, String key) {
        double percentage = getPercentageForKey(config, key);
        return new AttributeModifier(modifierData,
                percentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    private double getPercentageForKey(Stats config, String key) {
        return switch (key) {
            case "cracked_crown_max_health" -> config.percentageExtraMaxHealth;
            case "cracked_crown_movement_speed" -> config.percentageExtraSpeed;
            case "cracked_crown_flying_speed" -> config.percentageExtraFlySpeed;
            case "cracked_crown_attack_damage" -> config.percentageExtraAttack;
            case "cracked_crown_armor" -> config.percentageExtraArmor;
            case "cracked_crown_attack_speed" -> config.percentageExtraAttackSpeed;
            case "cracked_crown_armor_thougness" -> config.percentageExtraArmorToughness;
            case "cracked_crown_attack_knockback" -> config.percentageExtraAttackKnockback;
            case "cracked_crown_knockback_resistance" -> config.percentageExtraKnockbackResistance;
            case "cracked_crown_luck" -> config.percentageExtraLuck;
            case "cracked_crown_swim_speed" -> config.percentageExtraSwimSpeed;
            case "cracked_crown_block_reach" -> config.percentageExtraBlockReach;
            case "cracked_crown_entity_reach" -> config.percentageExtraEntityReach;
            case "cracked_crown_step_height_addition" -> config.percentageExtraStepHeightAddition;
            default -> 0;
        };
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        removeModifiers(reference.entity(), config);
    }

    private void removeModifiers(LivingEntity wearer, Stats config) {
        for (Map.Entry<String, List<String>> entry : modifiers.entrySet()) {
            for (String key : entry.getValue()) {
                AttributeInstance attribute = getAttribute(wearer, key);
                if (attribute != null) {
                    ResourceLocation modifierData = ResourceLocation.fromNamespaceAndPath(entry.getKey(), key);
                    AttributeModifier modifier = createAttributeModifier(modifierData, config, key);
                    EntityUtils.removeAttributeModifier(attribute, modifier);
                }
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public double percentageExtraMaxHealth = 50;
        public double percentageExtraSpeed = 50;
        public double percentageExtraFlySpeed = 0;
        public double percentageExtraAttack = 50;
        public double percentageExtraArmor = 50;
        public double percentageExtraAttackSpeed = 50;
        public double percentageExtraArmorToughness = 50;
        public double percentageExtraAttackKnockback = 50;
        public double percentageExtraKnockbackResistance = 50;
        public double percentageExtraLuck = 50;
        public double percentageExtraSwimSpeed = 0;
        public double percentageExtraBlockReach = 0;
        public double percentageExtraEntityReach = 0;
        public double percentageExtraStepHeightAddition = 0;

        public boolean isEnable = true;
    }
}