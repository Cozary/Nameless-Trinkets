package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Reforger extends ReforgerBase implements Accessory {

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = ReforgerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof Player player))
            return;

        if (!player.isSpectator()) {
            for (int i = 0; i < player.getInventory().items.size(); i++) {
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
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

}