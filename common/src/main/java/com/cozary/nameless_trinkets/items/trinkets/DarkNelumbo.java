package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DarkNelumbo extends TrinketItem<DarkNelumbo.Stats> {
    public static DarkNelumbo INSTANCE;

    public DarkNelumbo() {
        super(new TrinketData(null,null, Stats.class));

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
        if (reference.entity().isEyeInFluid(FluidTags.LAVA)) {
            Vec3 currentMovement = reference.entity().getDeltaMovement();

            Vec3 newMovement = new Vec3(currentMovement.x, 0.5D, currentMovement.z);

            reference.entity().setDeltaMovement(newMovement);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.nameless_trinkets.dark_nelumbo_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.dark_nelumbo_1").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
        }
    }

    public static class Stats extends TrinketsStats {
        public boolean isEnable = true;

    }
}
