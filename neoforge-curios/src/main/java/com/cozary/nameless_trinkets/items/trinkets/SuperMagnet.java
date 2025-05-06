package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class SuperMagnet extends TrinketItem<SuperMagnet.Stats> {
    public static SuperMagnet INSTANCE;

    public SuperMagnet() {
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
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        Stats config = SuperMagnet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Level world = livingEntity.level();

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, livingEntity.getBoundingBox().inflate(config.range));
        for (ItemEntity item : items) {
            if (!item.isAlive())
                continue;

            if (item.getOwner() != null && item.getOwner().equals(livingEntity.getUUID()) && item.hasPickUpDelay())
                continue;

            if (!world.isClientSide) {
                item.setNoPickUpDelay();
                item.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            }
        }

        List<ExperienceOrb> xporbs = world.getEntitiesOfClass(ExperienceOrb.class, livingEntity.getBoundingBox().inflate(config.range));
        for (ExperienceOrb orb : xporbs) {
            if (!world.isClientSide) {
                orb.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            }

        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (!trinketConfig.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.super_magnet_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.super_magnet_1", trinketConfig.range).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public double range = 50.0;
        public boolean isEnable = true;

    }

}