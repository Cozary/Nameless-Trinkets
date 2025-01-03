package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.TrueHeartOfTheSea;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class TrueHeartOfTheSeaEvents {

    @SubscribeEvent
    public static void function(PlayerEvent.BreakSpeed event) {
        TrueHeartOfTheSea.Stats config = TrueHeartOfTheSea.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!event.getEntity().isSpectator()) {
            Player player = event.getEntity();

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.TRUE_HEART_OF_THE_SEA.get());

            if ((!stack.isEmpty() && player.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value()))) {

                event.setNewSpeed(event.getOriginalSpeed() * config.miningUnderwaterSpeed);
            }
        }
    }
}
