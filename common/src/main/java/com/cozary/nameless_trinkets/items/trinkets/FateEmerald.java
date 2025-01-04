package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class FateEmerald extends TrinketItem<FateEmerald.Stats> {
    public static FateEmerald INSTANCE;

    public FateEmerald() {
        super(new TrinketData(new Item.Properties().stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "fate_emerald")))
                , null,
                Stats.class));
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
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = FateEmerald.INSTANCE.getTrinketConfig();
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
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = FateEmerald.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        config.timeUntilUnequip = 1200;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) {
            return;
        }

        Level world = player.level();
        Stats config = FateEmerald.INSTANCE.getTrinketConfig();

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
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) {
            return true;
        }

        Stats config = FateEmerald.INSTANCE.getTrinketConfig();

        if (config.timeUntilUnequip <= 0) {
            config.timeUntilUnequip = config.backupTimeUntilUnequip;
            config.resetTimerFlag = true;
        }

        return config.resetTimerFlag || player.getAbilities().instabuild;
    }


    public static class Stats extends TrinketsStats {
        public int discountPercentage = 100;
        public float timeUntilUnequip = 0;
        public float hungerExhaustionRate = 0.1F;
        public boolean isEnable = true;
        float backupTimeUntilUnequip = 0;
        boolean resetTimerFlag = false;
    }


}