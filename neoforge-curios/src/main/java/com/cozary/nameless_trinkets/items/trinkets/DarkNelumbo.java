package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class DarkNelumbo extends TrinketItemCurios<DarkNelumboBase.Stats> {
    public static DarkNelumboBase INSTANCE;

    public DarkNelumbo() {
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
        if (slotContext.entity().isEyeInFluid(FluidTags.LAVA)) {
            Vec3 currentMovement = slotContext.entity().getDeltaMovement();

            Vec3 newMovement = new Vec3(currentMovement.x, 0.5D, currentMovement.z);

            slotContext.entity().setDeltaMovement(newMovement);
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
        public boolean cancelLavaDamage = true;
        public boolean isEnable = true;

    }
}
