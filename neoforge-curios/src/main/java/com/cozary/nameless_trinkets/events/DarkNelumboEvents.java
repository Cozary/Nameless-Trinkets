package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class DarkNelumboEvents {

    @SubscribeEvent
    public static void blazeNucleusImmune(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        var stack = TrinketUtils.getEquippedTrinket(player, ModItems.DARK_NELUMBO.get());

        if (stack.isEmpty())
            return;

        event.setCanceled(DarkNelumboHandler.blazeNucleusImmune(player, event.getSource()));
    }
}
