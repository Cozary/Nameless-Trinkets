package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.events.WoundbearerHandler;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class Woundbearer extends WoundbearerBase implements Trinket {

    public Woundbearer() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = WoundbearerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

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
            AttributeModifier dummyModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                    0.0f, AttributeModifier.Operation.ADD_VALUE);
            CommonUtils.removeAttributeModifier(attributeDamage, dummyModifier);

            if (newDamage > 0) {
                AttributeModifier damageModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                        newDamage, AttributeModifier.Operation.ADD_VALUE);
                CommonUtils.applyAttributeModifier(attributeDamage, damageModifier);
            }
        }

    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {

        float damageIncrement = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue();
        if (damageIncrement > 0) {
            CommonUtils.removeAttributeModifier(Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE)),
                    new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                            damageIncrement, AttributeModifier.Operation.ADD_VALUE));
        }
    }


}