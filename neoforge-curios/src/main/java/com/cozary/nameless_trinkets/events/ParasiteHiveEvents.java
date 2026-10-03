package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ParasiteHiveEvents {

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.PARASITE_HIVE.get());
            if (!stack.isEmpty() && event.getSource().getEntity() instanceof LivingEntity attacker) {
                ParasiteHiveHandler.onPlayerAttacked(player, attacker);
            }
        }

        if (event.getSource().getEntity() instanceof Silverfish silverfish && ParasiteHiveHandler.isParasiteSilverfish(silverfish)) {
            if (ParasiteHiveHandler.isOwner(silverfish, event.getEntity()) || ParasiteHiveHandler.isParasiteSilverfish(event.getEntity())) {
                event.setCanceled(true);
                return;
            }
            ParasiteHiveHandler.onParasiteSilverfishAttack(silverfish);
        }
    }
}
