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
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class EtherealWings extends TrinketItemCurios<EtherealWingsBase.Stats> {
    public static EtherealWingsBase INSTANCE;

    public EtherealWings() {
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
    public void onEquip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        Stats config = EtherealWingsBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(slotContext.entity() instanceof Player player) || player.isCreative() || player.isSpectator()) {
            return;
        }

        player.getAbilities().mayfly = true;
        player.getAbilities().setFlyingSpeed(config.flyingSpeed);
        player.onUpdateAbilities();
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return;
        }

        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
        }

        player.getAbilities().setFlyingSpeed(0.05F);
        player.onUpdateAbilities();
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (!trinketConfig.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.ethereal_wings_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.ethereal_wings_1").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float flyingSpeed = 0.01F;
        public boolean isEnable = true;

    }

}