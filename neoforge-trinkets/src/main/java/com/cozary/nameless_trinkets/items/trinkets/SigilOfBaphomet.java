package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SigilOfBaphomet extends SigilOfBaphometBase implements TrinketCallback {

    public SigilOfBaphomet() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
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
