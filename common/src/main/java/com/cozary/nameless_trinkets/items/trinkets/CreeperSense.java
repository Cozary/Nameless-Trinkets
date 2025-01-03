package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CreeperSense extends TrinketItem<CreeperSense.Stats> {
    public static CreeperSense INSTANCE;

    public CreeperSense() {
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
        CreeperSense.Stats config = CreeperSense.INSTANCE.getTrinketConfig();

        if (!(reference.entity() instanceof ServerPlayer player) || player.isSpectator()) {
            return;
        }

        if (!config.isEnable) {
            return;
        }

        if (player.isCrouching()) {
            player.level().explode(null, player.getX(), player.getY(), player.getZ(), config.explosionLevel, Level.ExplosionInteraction.NONE);
            player.setShiftKeyDown(false);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = CreeperSense.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.creeper_sense_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.creeper_sense_1").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.creeper_sense_2").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float explosionLevel = 15.0F;
        public boolean isEnable = true;

    }

}