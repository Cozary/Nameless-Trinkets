package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

public class PufferFishLiver extends PufferFishLiverBase implements Trinket {

    public PufferFishLiver() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = PufferFishLiverBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!stack.isEmpty()) {
            if (entity.hasEffect(MobEffects.POISON)) {

                entity.removeEffect(MobEffects.POISON);
            }
        }

    }

}