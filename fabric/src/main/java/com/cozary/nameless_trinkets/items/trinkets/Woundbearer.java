package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class Woundbearer extends WoundbearerBase implements Accessory {

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = WoundbearerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        float damageIncrement = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue();

        if (damageIncrement > 0) {

            AttributeInstance attributeDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeModifier damageModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                    damageIncrement, AttributeModifier.Operation.ADD_VALUE);

            assert attributeDamage != null;
            EntityUtils.applyAttributeModifier(attributeDamage, damageModifier);
        }

    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {

        float damageIncrement = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue();
        if (damageIncrement > 0) {
            EntityUtils.removeAttributeModifier(Objects.requireNonNull(reference.entity().getAttribute(Attributes.ATTACK_DAMAGE)),
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                            damageIncrement, AttributeModifier.Operation.ADD_VALUE));
        }
    }


}