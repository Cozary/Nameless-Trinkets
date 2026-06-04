package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Reforger extends ReforgerBase implements Trinket {

    public Reforger() {
        super();
        }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = ReforgerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(entity instanceof Player player))
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


    

    

}