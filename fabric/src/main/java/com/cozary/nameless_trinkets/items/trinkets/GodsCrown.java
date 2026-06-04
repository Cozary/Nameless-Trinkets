package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GodsCrown extends GodsCrownBase implements Trinket {

    public GodsCrown() {
        super();
        }

    

    

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

        if (world.isClientSide()) return;

        applyModifiers(livingEntity, config);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(entity instanceof Player)) return;

        removeModifiers(entity, config);
    }
}