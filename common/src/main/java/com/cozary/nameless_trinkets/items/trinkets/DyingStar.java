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
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.Item;
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
        super(new TrinketData(new Item.Properties().stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dying_star"))), null, DyingStar.Stats.class));

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

        player.playSound(SoundEvents.GENERIC_EAT.value(), 1.0f, 0.1f);

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

    public enum AttributeSelector {
        ARMOR(Attributes.ARMOR, ModDataComponents.DYING_STAR_ARMOR.get(), 30.0f, 0.01f, 0.02f),
        ARMOR_TOUGHNESS(Attributes.ARMOR_TOUGHNESS, ModDataComponents.DYING_STAR_ARMOR_TOUGHNESS.get(), 20.0f, 0.01f, 0.02f),
        ATTACK_DAMAGE(Attributes.ATTACK_DAMAGE, ModDataComponents.DYING_STAR_ATTACK_DAMAGE.get(), 2048.0f, 0.009f, 0.01f),
        ATTACK_KNOCKBACK(Attributes.ATTACK_KNOCKBACK, ModDataComponents.DYING_STAR_ATTACK_KNOCKBACK.get(), 5.0f, 0.01f, 0.02f),
        ATTACK_SPEED(Attributes.ATTACK_SPEED, ModDataComponents.DYING_STAR_ATTACK_SPEED.get(), 1024.0f, 0.01f, 0.04f),
        BLOCK_BREAK_SPEED(Attributes.BLOCK_BREAK_SPEED, ModDataComponents.DYING_STAR_BLOCK_BREAK_SPEED.get(), 1024.0f, 0.009f, 0.01f),
        BLOCK_INTERACTION_RANGE(Attributes.BLOCK_INTERACTION_RANGE, ModDataComponents.DYING_STAR_BLOCK_INTERACTION_RANGE.get(), 1.0f, 0.01f, 0.045f),
        EXPLOSION_KNOCKBACK_RESISTANCE(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, ModDataComponents.DYING_STAR_EXPLOSION_KNOCKBACK_RESISTANCE.get(), 1.0f, 0.01f, 0.02f),
        ENTITY_INTERACTION_RANGE(Attributes.ENTITY_INTERACTION_RANGE, ModDataComponents.DYING_STAR_ENTITY_INTERACTION_RANGE.get(), 64.0f, 0.01f, 0.03f),
        FALL_DAMAGE_MULTIPLIER(Attributes.FALL_DAMAGE_MULTIPLIER, ModDataComponents.DYING_STAR_FALL_DAMAGE_MULTIPLIER.get(), 100.0f, 0.009f, 0.01f),
        FLYING_SPEED(Attributes.FLYING_SPEED, ModDataComponents.DYING_STAR_FLYING_SPEED.get(), 1024.0f, 0.0001f, 0.0004f),
        KNOCKBACK_RESISTANCE(Attributes.KNOCKBACK_RESISTANCE, ModDataComponents.DYING_STAR_KNOCKBACK_RESISTANCE.get(), 1.0f, 0.01f, 0.02f),
        LUCK(Attributes.LUCK, ModDataComponents.DYING_STAR_LUCK.get(), 1024.0f, 0.009f, 0.01f),
        MAX_ABSORPTION(Attributes.MAX_ABSORPTION, ModDataComponents.DYING_STAR_MAX_ABSORPTION.get(), 2048.0f, 0.01f, 0.02f),
        MAX_HEALTH(Attributes.MAX_HEALTH, ModDataComponents.DYING_STAR_MAX_HEALTH.get(), 1024.0f, 0.01f, 0.2f),
        MINING_EFFICIENCY(Attributes.MINING_EFFICIENCY, ModDataComponents.DYING_STAR_MINING_EFFICIENCY.get(), 1024.0f, 0.01f, 0.02f),
        MOVEMENT_SPEED(Attributes.MOVEMENT_SPEED, ModDataComponents.DYING_STAR_MOVEMENT_SPEED.get(), 1024.0f, 0.001f, 0.007f),
        OXYGEN_BONUS(Attributes.OXYGEN_BONUS, ModDataComponents.DYING_STAR_OXYGEN_BONUS.get(), 1024.0f, 0.01f, 0.02f),
        SNEAKING_SPEED(Attributes.SNEAKING_SPEED, ModDataComponents.DYING_STAR_SNEAKING_SPEED.get(), 1.0f, 0.001f, 0.003f),
        SUBMERGED_MINING_SPEED(Attributes.SUBMERGED_MINING_SPEED, ModDataComponents.DYING_STAR_SUBMERGED_MINING_SPEED.get(), 20.0f, 0.001f, 0.002f),
        SWEEPING_DAMAGE_RATIO(Attributes.SWEEPING_DAMAGE_RATIO, ModDataComponents.DYING_STAR_SWEEPING_DAMAGE_RATIO.get(), 1.0f, 0.01f, 0.02f),
        WATER_MOVEMENT_EFFICIENCY(Attributes.WATER_MOVEMENT_EFFICIENCY, ModDataComponents.DYING_STAR_WATER_MOVEMENT_EFFICIENCY.get(), 1.0f, 0.01f, 0.02f);

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
    }

}