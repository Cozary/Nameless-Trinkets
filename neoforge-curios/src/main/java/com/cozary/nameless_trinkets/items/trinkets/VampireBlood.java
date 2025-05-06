package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Objects;

public class VampireBlood extends TrinketItemCurios<VampireBloodBase.Stats> {
    public static VampireBloodBase INSTANCE;

    public VampireBlood() {
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
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.vampire_blood_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.vampire_blood_1", config.damageMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.vampire_blood_2", config.healingPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.vampire_blood_3").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {

        Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();

        if (!livingEntity.isSpectator()) {


            if (!stack.isEmpty() && livingEntity instanceof ServerPlayer) {

                if (livingEntity.level().isDay() && livingEntity.level().canSeeSky(livingEntity.blockPosition()) && !livingEntity.level().isClientSide) {

                    ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1, 1D, 1D, 1D, 0.01);
                    livingEntity.hurt(livingEntity.damageSources().onFire(), (float) config.sunDamage);

                }
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "vampire_blood_attack_damage"),
                config.damageMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        EntityUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "vampire_blood_attack_damage"),
                        trinketConfig.damageMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }


    public static class Stats extends TrinketsStats {
        public double damageMultiplierPercentage = 150.0f;
        public float healingPercentage = 10.0f;
        public double sunDamage = 2.0f;
        public boolean isEnable = true;

    }
}