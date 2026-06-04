package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EtherealWings extends EtherealWingsBase implements Trinket {

    public EtherealWings() {
        super();
        }

    

    

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = EtherealWingsBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(entity instanceof Player player) || player.isCreative() || player.isSpectator()) {
            return;
        }

        player.getAbilities().mayfly = true;
        player.getAbilities().setFlyingSpeed(config.flyingSpeed);
        player.onUpdateAbilities();
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
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