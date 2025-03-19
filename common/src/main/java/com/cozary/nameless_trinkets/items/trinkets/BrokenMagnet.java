package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrokenMagnet extends TrinketItem<BrokenMagnet.Stats> {
    public static BrokenMagnet INSTANCE;

    public BrokenMagnet() {
        super(new TrinketData(null, null, Stats.class));

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
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = BrokenMagnet.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Level world = reference.entity().level();

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, reference.entity().getBoundingBox().inflate(config.range));
        for (ItemEntity item : items) {
            if (!item.isAlive())
                continue;

            if (item.getOwner() != null && item.getOwner().equals(reference.entity().getUUID()) && item.hasPickUpDelay())
                continue;

            if (!world.isClientSide) {
                item.setNoPickUpDelay();
                item.setPos(reference.entity().getX(), reference.entity().getY(), reference.entity().getZ());
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = BrokenMagnet.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.broken_magnet_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.broken_magnet_1", config.range).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float range = 25.0F;
        public boolean isEnable = true;
    }

}