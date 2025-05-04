package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.MoonStone;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class MoonStoneEvents {

    @SubscribeEvent
    public static void MoonStoneDamage(LivingIncomingDamageEvent event) {
        MoonStone.Stats config = MoonStone.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {
            if (event.getEntity() == player) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return;
                }
                var stack = accessories.getEquipped(ModItems.MOON_STONE.get());
                if (!stack.isEmpty()) {
                    if (event.getSource().is(DamageTypes.FALL)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
