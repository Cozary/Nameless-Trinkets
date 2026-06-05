package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EtherealWings extends EtherealWingsBase implements TrinketCallback {

    public EtherealWings() {
        super();
    }


    @Override
    public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        Stats config = EtherealWingsBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(entity instanceof Player player) || player.isCreative() || player.isSpectator()) {
            return;
        }

        player.getAbilities().mayfly = true;
        player.getAbilities().setFlyingSpeed(config.flyingSpeed);
        player.onUpdateAbilities();
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
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
