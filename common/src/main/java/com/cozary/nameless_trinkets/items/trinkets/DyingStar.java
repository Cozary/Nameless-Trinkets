package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.cozary.nameless_trinkets.init.ModTags.NAMELESS_TRINKETS_TAG;

public class DyingStar extends TrinketItem<DyingStar.Stats> {
    private static final Random random = new Random();
    public static DyingStar INSTANCE;

    public DyingStar() {
        super(new TrinketData(null, null, DyingStar.Stats.class));

        INSTANCE = this;
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
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        Stats config = DyingStar.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return false;

        applyRandomStat(other, stack, player);
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    private void applyRandomStat(@Nullable ItemStack other, ItemStack stack, Player player) {

        if (stack.isEmpty())
            return;

        if (other != null) {
            if (!other.is(NAMELESS_TRINKETS_TAG))
                return;

            other.shrink(1);
        }

        player.playSound(SoundEvents.GENERIC_EAT, 1.0f, 0.1f);

        List<AttributeSelector> attributes = new ArrayList<>(List.of(AttributeSelector.values()));
        Collections.shuffle(attributes, random);

        boolean applied = false;

        for (AttributeSelector attributeSelector : attributes) {
            float currentValue = stack.getOrDefault(attributeSelector.getDataComponentType(), 0.0f);
            if (currentValue < attributeSelector.getMaxValue()) {
                float increment = random.nextFloat(attributeSelector.getMinIncrease(), attributeSelector.getMaxIncrease());

                float newValue = Math.min(currentValue + increment, attributeSelector.getMaxValue());
                stack.set(attributeSelector.getDataComponentType(), newValue);
                applied = true;
                break;
            }
        }

        if (!applied) {
            player.displayClientMessage(Component.translatable("message.nameless_trinkets.dying_star_all_maxed").withStyle(ChatFormatting.BLACK), true);
        }
    }


    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = DyingStar.INSTANCE.getTrinketConfig();
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
                    EntityUtils.applyAttributeModifier(attributeDamage, attributeModifier);
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
                    EntityUtils.removeAttributeModifier(attributeDamage, attributeModifier);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = DyingStar.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.dying_star_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                if (Minecraft.getInstance().player.tickCount % 5 == 0) {
                    tooltip.add(Component.translatable("tooltip.nameless_trinkets.dying_star_1").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                } else {
                    tooltip.add(Component.translatable("tooltip.nameless_trinkets.dying_star_1").withStyle(ChatFormatting.GOLD, ChatFormatting.OBFUSCATED));

                }
            } else if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_LCONTROL)) {
                Map<String, Float> stats = new HashMap<>();
                stats.put("Max Health: +", stack.getOrDefault(ModDataComponents.DYING_STAR_MAX_HEALTH.get(), 0.0f));
                stats.put("Movement Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_MOVEMENT_SPEED.get(), 0.0f));
                stats.put("Flying Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_FLYING_SPEED.get(), 0.0f));
                stats.put("Attack Damage: +", stack.getOrDefault(ModDataComponents.DYING_STAR_ATTACK_DAMAGE.get(), 0.0f));
                stats.put("Armor: +", stack.getOrDefault(ModDataComponents.DYING_STAR_ARMOR.get(), 0.0f));
                stats.put("Attack Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_ATTACK_SPEED.get(), 0.0f));
                stats.put("Armor Toughness: +", stack.getOrDefault(ModDataComponents.DYING_STAR_ARMOR_TOUGHNESS.get(), 0.0f));
                stats.put("Attack Knockback: +", stack.getOrDefault(ModDataComponents.DYING_STAR_ATTACK_KNOCKBACK.get(), 0.0f));
                stats.put("Knockback Resistance: +", stack.getOrDefault(ModDataComponents.DYING_STAR_KNOCKBACK_RESISTANCE.get(), 0.0f));
                stats.put("Luck: +", stack.getOrDefault(ModDataComponents.DYING_STAR_LUCK.get(), 0.0f));
                stats.put("Swim Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_WATER_MOVEMENT_EFFICIENCY.get(), 0.0f));
                stats.put("Block Reach: +", stack.getOrDefault(ModDataComponents.DYING_STAR_BLOCK_INTERACTION_RANGE.get(), 0.0f));
                stats.put("Entity Reach: +", stack.getOrDefault(ModDataComponents.DYING_STAR_ENTITY_INTERACTION_RANGE.get(), 0.0f));
                stats.put("Step Height Addition: +", stack.getOrDefault(ModDataComponents.DYING_STAR_SNEAKING_SPEED.get(), 0.0f));
                stats.put("Block Break Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_BLOCK_BREAK_SPEED.get(), 0.0f));
                stats.put("Explosion Knockback Resistance: +", stack.getOrDefault(ModDataComponents.DYING_STAR_EXPLOSION_KNOCKBACK_RESISTANCE.get(), 0.0f));
                stats.put("Fall Damage Multiplier: +", stack.getOrDefault(ModDataComponents.DYING_STAR_FALL_DAMAGE_MULTIPLIER.get(), 0.0f));
                stats.put("Mining Efficiency: +", stack.getOrDefault(ModDataComponents.DYING_STAR_MINING_EFFICIENCY.get(), 0.0f));
                stats.put("Oxygen Bonus: +", stack.getOrDefault(ModDataComponents.DYING_STAR_OXYGEN_BONUS.get(), 0.0f));
                stats.put("Sneaking Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_SNEAKING_SPEED.get(), 0.0f));
                stats.put("Submerged Mining Speed: +", stack.getOrDefault(ModDataComponents.DYING_STAR_SUBMERGED_MINING_SPEED.get(), 0.0f));
                stats.put("Sweeping Damage Ratio: +", stack.getOrDefault(ModDataComponents.DYING_STAR_SWEEPING_DAMAGE_RATIO.get(), 0.0f));
                stats.put("Water Movement Efficiency: +", stack.getOrDefault(ModDataComponents.DYING_STAR_WATER_MOVEMENT_EFFICIENCY.get(), 0.0f));

                for (Map.Entry<String, Float> entry : stats.entrySet()) {

                    tooltip.add(Component.translatable(entry.getKey() + entry.getValue()).withStyle(ChatFormatting.GOLD));

                }
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_ctrl_l"));
            }
        }
    }

    static Stats config = DyingStar.INSTANCE.getTrinketConfig();

    public enum AttributeSelector {
        ARMOR(Attributes.ARMOR, ModDataComponents.DYING_STAR_ARMOR.get(), config.armorMaxValue, config.armorMinIncrease, config.armorMaxIncrease),
        ARMOR_TOUGHNESS(Attributes.ARMOR_TOUGHNESS, ModDataComponents.DYING_STAR_ARMOR_TOUGHNESS.get(), config.armorToughnessMaxValue, config.armorToughnessMinIncrease, config.armorToughnessMaxIncrease),
        ATTACK_DAMAGE(Attributes.ATTACK_DAMAGE, ModDataComponents.DYING_STAR_ATTACK_DAMAGE.get(), config.attackDamageMaxValue, config.attackDamageMinIncrease, config.attackDamageMaxIncrease),
        ATTACK_KNOCKBACK(Attributes.ATTACK_KNOCKBACK, ModDataComponents.DYING_STAR_ATTACK_KNOCKBACK.get(), config.attackKnockbackMaxValue, config.attackKnockbackMinIncrease, config.attackKnockbackMaxIncrease),
        ATTACK_SPEED(Attributes.ATTACK_SPEED, ModDataComponents.DYING_STAR_ATTACK_SPEED.get(), config.attackSpeedMaxValue, config.attackSpeedMinIncrease, config.attackSpeedMaxIncrease),
        BLOCK_BREAK_SPEED(Attributes.BLOCK_BREAK_SPEED, ModDataComponents.DYING_STAR_BLOCK_BREAK_SPEED.get(), config.blockBreakSpeedMaxValue, config.blockBreakSpeedMinIncrease, config.blockBreakSpeedMaxIncrease),
        BLOCK_INTERACTION_RANGE(Attributes.BLOCK_INTERACTION_RANGE, ModDataComponents.DYING_STAR_BLOCK_INTERACTION_RANGE.get(), config.blockInteractionRangeMaxValue, config.blockInteractionRangeMinIncrease, config.blockInteractionRangeMaxIncrease),
        EXPLOSION_KNOCKBACK_RESISTANCE(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, ModDataComponents.DYING_STAR_EXPLOSION_KNOCKBACK_RESISTANCE.get(), config.explosionKnockbackResistanceMaxValue, config.explosionKnockbackResistanceMinIncrease, config.explosionKnockbackResistanceMaxIncrease),
        ENTITY_INTERACTION_RANGE(Attributes.ENTITY_INTERACTION_RANGE, ModDataComponents.DYING_STAR_ENTITY_INTERACTION_RANGE.get(), config.entityInteractionRangeMaxValue, config.entityInteractionRangeMinIncrease, config.entityInteractionRangeMaxIncrease),
        FALL_DAMAGE_MULTIPLIER(Attributes.FALL_DAMAGE_MULTIPLIER, ModDataComponents.DYING_STAR_FALL_DAMAGE_MULTIPLIER.get(), config.fallDamageMultiplierMaxValue, config.fallDamageMultiplierMinIncrease, config.fallDamageMultiplierMaxIncrease),
        FLYING_SPEED(Attributes.FLYING_SPEED, ModDataComponents.DYING_STAR_FLYING_SPEED.get(), config.flyingSpeedMaxValue, config.flyingSpeedMinIncrease, config.flyingSpeedMaxIncrease),
        KNOCKBACK_RESISTANCE(Attributes.KNOCKBACK_RESISTANCE, ModDataComponents.DYING_STAR_KNOCKBACK_RESISTANCE.get(), config.knockbackResistanceMaxValue, config.knockbackResistanceMinIncrease, config.knockbackResistanceMaxIncrease),
        LUCK(Attributes.LUCK, ModDataComponents.DYING_STAR_LUCK.get(), config.luckMaxValue, config.luckMinIncrease, config.luckMaxIncrease),
        MAX_ABSORPTION(Attributes.MAX_ABSORPTION, ModDataComponents.DYING_STAR_MAX_ABSORPTION.get(), config.maxAbsorptionMaxValue, config.maxAbsorptionMinIncrease, config.maxAbsorptionMaxIncrease),
        MAX_HEALTH(Attributes.MAX_HEALTH, ModDataComponents.DYING_STAR_MAX_HEALTH.get(), config.maxHealthMaxValue, config.maxHealthMinIncrease, config.maxHealthMaxIncrease),
        MINING_EFFICIENCY(Attributes.MINING_EFFICIENCY, ModDataComponents.DYING_STAR_MINING_EFFICIENCY.get(), config.miningEfficiencyMaxValue, config.miningEfficiencyMinIncrease, config.miningEfficiencyMaxIncrease),
        MOVEMENT_SPEED(Attributes.MOVEMENT_SPEED, ModDataComponents.DYING_STAR_MOVEMENT_SPEED.get(), config.movementSpeedMaxValue, config.movementSpeedMinIncrease, config.movementSpeedMaxIncrease),
        OXYGEN_BONUS(Attributes.OXYGEN_BONUS, ModDataComponents.DYING_STAR_OXYGEN_BONUS.get(), config.oxygenBonusMaxValue, config.oxygenBonusMinIncrease, config.oxygenBonusMaxIncrease),
        SNEAKING_SPEED(Attributes.SNEAKING_SPEED, ModDataComponents.DYING_STAR_SNEAKING_SPEED.get(), config.sneakingSpeedMaxValue, config.sneakingSpeedMinIncrease, config.sneakingSpeedMaxIncrease),
        SUBMERGED_MINING_SPEED(Attributes.SUBMERGED_MINING_SPEED, ModDataComponents.DYING_STAR_SUBMERGED_MINING_SPEED.get(), config.submergedMiningSpeedMaxValue, config.submergedMiningSpeedMinIncrease, config.submergedMiningSpeedMaxIncrease),
        SWEEPING_DAMAGE_RATIO(Attributes.SWEEPING_DAMAGE_RATIO, ModDataComponents.DYING_STAR_SWEEPING_DAMAGE_RATIO.get(), config.sweepingDamageRatioMaxValue, config.sweepingDamageRatioMinIncrease, config.sweepingDamageRatioMaxIncrease),
        WATER_MOVEMENT_EFFICIENCY(Attributes.WATER_MOVEMENT_EFFICIENCY, ModDataComponents.DYING_STAR_WATER_MOVEMENT_EFFICIENCY.get(), config.waterMovementEfficiencyMaxValue, config.waterMovementEfficiencyMinIncrease, config.waterMovementEfficiencyMaxIncrease);

        private final Holder<Attribute> attributeHolder;
        private final DataComponentType<Float> dataComponentType;
        private final Float maxValue;
        private final Float minIncrease;
        private final Float maxIncrease;

        AttributeSelector(Holder<Attribute> attributeHolder, DataComponentType<Float> dataComponentType, Float maxValue, Float minIncrease, Float maxIncrease) {
            this.attributeHolder = attributeHolder;
            this.dataComponentType = dataComponentType;
            this.maxValue = maxValue;
            this.minIncrease = minIncrease;
            this.maxIncrease = maxIncrease;
        }

        public Holder<Attribute> getAttributeHolder() {
            return attributeHolder;
        }

        public DataComponentType<Float> getDataComponentType() {
            return dataComponentType;
        }

        public Float getMaxValue() {
            return maxValue;
        }

        public Float getMinIncrease() {
            return minIncrease;
        }

        public Float getMaxIncrease() {
            return maxIncrease;
        }
    }

    public static class Stats extends TrinketsStats {
        public boolean isEnable = true;

        public float armorMaxValue = 30.0f;
        public float armorMinIncrease = 0.01f;
        public float armorMaxIncrease = 0.02f;

        public float armorToughnessMaxValue = 20.0f;
        public float armorToughnessMinIncrease = 0.01f;
        public float armorToughnessMaxIncrease = 0.02f;

        public float attackDamageMaxValue = 2048.0f;
        public float attackDamageMinIncrease = 0.009f;
        public float attackDamageMaxIncrease = 0.01f;

        public float attackKnockbackMaxValue = 5.0f;
        public float attackKnockbackMinIncrease = 0.01f;
        public float attackKnockbackMaxIncrease = 0.02f;

        public float attackSpeedMaxValue = 1024.0f;
        public float attackSpeedMinIncrease = 0.01f;
        public float attackSpeedMaxIncrease = 0.04f;

        public float blockBreakSpeedMaxValue = 1024.0f;
        public float blockBreakSpeedMinIncrease = 0.009f;
        public float blockBreakSpeedMaxIncrease = 0.01f;

        public float blockInteractionRangeMaxValue = 1.0f;
        public float blockInteractionRangeMinIncrease = 0.01f;
        public float blockInteractionRangeMaxIncrease = 0.045f;

        public float explosionKnockbackResistanceMaxValue = 1.0f;
        public float explosionKnockbackResistanceMinIncrease = 0.01f;
        public float explosionKnockbackResistanceMaxIncrease = 0.02f;

        public float entityInteractionRangeMaxValue = 64.0f;
        public float entityInteractionRangeMinIncrease = 0.01f;
        public float entityInteractionRangeMaxIncrease = 0.03f;

        public float fallDamageMultiplierMaxValue = 100.0f;
        public float fallDamageMultiplierMinIncrease = 0.009f;
        public float fallDamageMultiplierMaxIncrease = 0.01f;

        public float flyingSpeedMaxValue = 1024.0f;
        public float flyingSpeedMinIncrease = 0.0001f;
        public float flyingSpeedMaxIncrease = 0.0004f;

        public float knockbackResistanceMaxValue = 1.0f;
        public float knockbackResistanceMinIncrease = 0.01f;
        public float knockbackResistanceMaxIncrease = 0.02f;

        public float luckMaxValue = 1024.0f;
        public float luckMinIncrease = 0.009f;
        public float luckMaxIncrease = 0.01f;

        public float maxAbsorptionMaxValue = 2048.0f;
        public float maxAbsorptionMinIncrease = 0.01f;
        public float maxAbsorptionMaxIncrease = 0.02f;

        public float maxHealthMaxValue = 1024.0f;
        public float maxHealthMinIncrease = 0.01f;
        public float maxHealthMaxIncrease = 0.2f;

        public float miningEfficiencyMaxValue = 1024.0f;
        public float miningEfficiencyMinIncrease = 0.01f;
        public float miningEfficiencyMaxIncrease = 0.02f;

        public float movementSpeedMaxValue = 1024.0f;
        public float movementSpeedMinIncrease = 0.001f;
        public float movementSpeedMaxIncrease = 0.007f;

        public float oxygenBonusMaxValue = 1024.0f;
        public float oxygenBonusMinIncrease = 0.01f;
        public float oxygenBonusMaxIncrease = 0.02f;

        public float sneakingSpeedMaxValue = 1.0f;
        public float sneakingSpeedMinIncrease = 0.001f;
        public float sneakingSpeedMaxIncrease = 0.003f;

        public float submergedMiningSpeedMaxValue = 20.0f;
        public float submergedMiningSpeedMinIncrease = 0.001f;
        public float submergedMiningSpeedMaxIncrease = 0.002f;

        public float sweepingDamageRatioMaxValue = 1.0f;
        public float sweepingDamageRatioMinIncrease = 0.01f;
        public float sweepingDamageRatioMaxIncrease = 0.02f;

        public float waterMovementEfficiencyMaxValue = 1.0f;
        public float waterMovementEfficiencyMinIncrease = 0.01f;
        public float waterMovementEfficiencyMaxIncrease = 0.02f;
    }


}