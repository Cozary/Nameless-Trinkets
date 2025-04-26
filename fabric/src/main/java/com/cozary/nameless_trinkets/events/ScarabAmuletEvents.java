package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ScarabAmulet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;

public class ScarabAmuletEvents {

    public static void register() {
        ModEvents.TargetingCallback.EVENT.register((attacker, target) -> {
            ScarabAmulet.Stats config = ScarabAmulet.INSTANCE.getTrinketConfig();
            if (!config.isEnable)
                return true;

            if (target instanceof Player player) {
                if (!player.level().isClientSide) {

                    var accessories = AccessoriesCapability.get(player);

                    if (accessories == null) {
                        return true;
                    }
                    var stack = accessories.getEquipped(ModItems.SCARAB_AMULET.get());
                    if (!stack.isEmpty() && attacker instanceof Husk) {
                        return false;
                    }
                }

            }
            return true;
        });
    }
}
