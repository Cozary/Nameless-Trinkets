package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.LightGloves;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LightGlovesEvents {

    @SubscribeEvent
    public static void LightGlovesSpeedBreak(PlayerEvent.BreakSpeed event) {
        LightGloves.Stats config = LightGloves.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Player player = event.getEntity();

        if (!player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.LIGHT_GLOVES.get());

            if ((!stack.isEmpty() && !player.level().canSeeSky(player.blockPosition()))) {
                event.setNewSpeed(event.getOriginalSpeed() * config.miningSpeedMultiplier);
            }

        }
    }
}
