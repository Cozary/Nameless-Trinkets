package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FracturedNullstone;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class FracturedNullstoneEvents {

    @SubscribeEvent
    public static void reduceMagicDamage(LivingDamageEvent.Pre event) {
        FracturedNullstone.Stats config = FracturedNullstone.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {
            if (event.getEntity() == player) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.FRACTURED_NULLSTONE.get());

                if (!stack.isEmpty()) {

                    //haha DamageTypeTag Magic doesn't exist

                    if (event.getSource().type().msgId().equals("indirectMagic") || event.getSource().type().msgId().equals("magic")) {
                        event.setNewDamage(event.getOriginalDamage() * config.magicDamageReduction);
                    }
                }
            }
        }
    }
}
