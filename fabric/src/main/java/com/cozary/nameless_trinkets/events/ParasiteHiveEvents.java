package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;

public class ParasiteHiveEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            if (targetEntity instanceof Player player) {
                var stack = TrinketUtils.getEquippedTrinket(player, ModItems.PARASITE_HIVE.get());
                if (!stack.isEmpty() && damageSource.getEntity() instanceof LivingEntity attacker) {
                    ParasiteHiveHandler.onPlayerAttacked(player, attacker);
                }
            }

            if (damageSource.getEntity() instanceof Silverfish silverfish && ParasiteHiveHandler.isParasiteSilverfish(silverfish)) {
                if (ParasiteHiveHandler.isOwner(silverfish, targetEntity) || ParasiteHiveHandler.isParasiteSilverfish(targetEntity)) {
                    return 0.0f;
                }
                ParasiteHiveHandler.onParasiteSilverfishAttack(silverfish);
            }

            return amount;
        });
    }
}
