package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.AmphibiousHands;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class AmphibiousHandsEvents {

    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        AmphibiousHands.Stats config = AmphibiousHands.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Player player = event.getEntity();

        if (!player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.AMPHIBIOUS_HANDS.get());

            if ((!stack.isEmpty() && player.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value()))) {

                event.setNewSpeed(event.getOriginalSpeed() * config.miningUnderwaterSpeedMultiplier);
            }

        }
    }

}