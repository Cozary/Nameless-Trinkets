package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class EtherealWings extends EtherealWingsBase implements ICurioItem {


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

}