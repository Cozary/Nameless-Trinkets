package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public class SigilOfBaphomet extends SigilOfBaphometBase implements Trinket {

    public SigilOfBaphomet() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = SigilOfBaphometBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(entity instanceof ServerPlayer))
            return;

        if (!stack.isEmpty() && stack.getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0) {

            stack.set(ModDataComponents.SIGIL_COUNT.get(), stack.getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) - 1);

        }
    }

}