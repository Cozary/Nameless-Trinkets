package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.events.WoundbearerHandler;
import com.cozary.nameless_trinkets.init.ModDataComponents;
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

public class Woundbearer extends WoundbearerBase implements ICurioItem {

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
        Stats config = WoundbearerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        float currentDamage = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0.0f);
        float newDamage = currentDamage;

        int lastDamageTick = WoundbearerHandler.LAST_DAMAGE_TICKS.getOrDefault(livingEntity.getUUID(), 0);
        int ticksSinceDamage = livingEntity.tickCount - lastDamageTick;

        if ((ticksSinceDamage < 0 || ticksSinceDamage >= config.decayDelayTicks) && currentDamage > 0) {
            float ratio = Math.max(0.0f, Math.min(1.0f, currentDamage / config.maxDamageLimit));
            float decay = config.baseDecayAmount * (1.0f + 3.0f * ratio * ratio);
            newDamage = Math.max(0.0f, currentDamage - decay);

            stack.set(ModDataComponents.WOUNDBEARER_DAMAGE.get(), newDamage);
        }

        AttributeInstance attributeDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attributeDamage != null) {
            AttributeModifier dummyModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                    0.0f, AttributeModifier.Operation.ADD_VALUE);
            CommonUtils.removeAttributeModifier(attributeDamage, dummyModifier);

            if (newDamage > 0) {
                AttributeModifier damageModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                        newDamage, AttributeModifier.Operation.ADD_VALUE);
                CommonUtils.applyAttributeModifier(attributeDamage, damageModifier);
            }
        }

    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {

        float damageIncrement = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue();
        if (damageIncrement > 0) {
            CommonUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE)),
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                            damageIncrement, AttributeModifier.Operation.ADD_VALUE));
        }
    }


}