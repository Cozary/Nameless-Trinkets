package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class BrokenAnkhEvents {

    @SubscribeEvent
    public static void stopDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BROKEN_ANKH.get());

            if (stack.isEmpty())
                return;

            if (BrokenAnkhHandler.tryPreventDeath(player, stack.getFirst().stack().getItem())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BROKEN_ANKH.get());

            if (stack.isEmpty())
                return;

            BrokenAnkhHandler.restoreCooldownOnLogin(player, stack.getFirst().stack().getItem());
        }
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.BROKEN_ANKH.get());

            if (stack.isEmpty())
                return;

            BrokenAnkhHandler.saveCooldownOnLogout(player, stack.getFirst().stack().getItem());
        }
    }
}

