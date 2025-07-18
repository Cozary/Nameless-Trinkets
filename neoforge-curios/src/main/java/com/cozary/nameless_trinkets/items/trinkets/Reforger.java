package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Reforger extends ReforgerBase implements ICurioItem {

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = ReforgerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(slotContext.entity() instanceof Player player))
            return;

        if (!player.isSpectator()) {
            for (int i = 0; i < player.getInventory().getNonEquipmentItems().size(); i++) {
                ItemStack itemstack = player.getInventory().getItem(i);

                if (itemstack.isDamaged()) {
                    if (player.tickCount % (config.repairSpeedSeconds * 20) == 0) {
                        int x = Math.min(config.repairQuantity, itemstack.getDamageValue());
                        itemstack.setDamageValue(itemstack.getDamageValue() - x);
                    }
                }
            }
        }
    }


    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

}