package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class FateEmerald extends TrinketItemCurios<FateEmeraldBase.Stats> {
    public static FateEmeraldBase INSTANCE;

    public FateEmerald() {
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
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.fate_emerald_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fate_emerald_1").withStyle(ChatFormatting.GOLD));

                String formattedSeconds = String.format("%.2f", config.timeUntilUnequip / 20.0);
                String formattedHunger = String.format("%.2f", config.hungerExhaustionRate);

                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fate_emerald_2", formattedSeconds).withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fate_emerald_3", formattedHunger).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }


    @Override
    public void onEquip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        config.resetTimerFlag = false;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return;
        }

        Level world = player.level();
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (config.backupTimeUntilUnequip == 0) {
            config.backupTimeUntilUnequip = config.timeUntilUnequip;
        }

        if (config.timeUntilUnequip > 0) {
            config.timeUntilUnequip--;
        }

        if (!world.isClientSide() && player.tickCount % 20 == 0) {
            player.causeFoodExhaustion(config.hungerExhaustionRate);
        }
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return true;
        }

        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (config.timeUntilUnequip <= 0) {
            config.timeUntilUnequip = config.backupTimeUntilUnequip;
            config.resetTimerFlag = true;
        }

        return config.resetTimerFlag || player.getAbilities().instabuild;
    }


    public static class Stats extends TrinketsStats {
        public int discountBoost = 100;
        public float timeUntilUnequip = 1200;
        public float hungerExhaustionRate = 0.1F;
        public boolean isEnable = true;
        float backupTimeUntilUnequip = 0;
        boolean resetTimerFlag = false;
    }


}