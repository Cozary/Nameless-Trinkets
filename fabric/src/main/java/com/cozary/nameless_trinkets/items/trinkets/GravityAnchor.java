package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.utils.CommonUtils;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class GravityAnchor extends GravityAnchorBase implements Accessory {

    public GravityAnchor() {
        super();
        AccessoryRegistry.register(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.level();

        if (world.isClientSide()) return;

        applyModifiers(livingEntity, config);
    }

    @Override
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

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        removeModifiers(reference.entity(), config);
    }

    @Override
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
}
