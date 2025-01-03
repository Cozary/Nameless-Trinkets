package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class Reforger extends TrinketItem<Reforger.Stats> {
    public static Reforger INSTANCE;

    public Reforger() {
        super(new TrinketData(null,null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = Reforger.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof Player player))
            return;

        if (!player.isSpectator()) {
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack itemstack = player.getInventory().getItem(i);

                if (itemstack.isDamaged()) {
                    if (player.tickCount % (config.repairSpeedSeconds * 20) == 0) {
                        int x = Math.min(config.repairCuantity, itemstack.getDamageValue());
                        itemstack.setDamageValue(itemstack.getDamageValue() - x);
                    }
                }
            }
        }
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
        Stats config = Reforger.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.reforger_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.reforger_1").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int repairSpeedSeconds = 10;
        public int repairCuantity = 1;
        public boolean isEnable = true;

    }

}