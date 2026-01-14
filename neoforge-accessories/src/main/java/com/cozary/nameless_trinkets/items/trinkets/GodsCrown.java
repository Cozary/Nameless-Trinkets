package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GodsCrown extends GodsCrownBase implements Accessory {

    public GodsCrown() {
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
    public void onUnequip(ItemStack stack, SlotReference reference) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(reference.entity() instanceof Player)) return;

        removeModifiers(reference.entity(), config);
    }
}