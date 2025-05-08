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
            if(target instanceof Player player){
                if(ScarabAmuletHandler.shouldPreventHuskTargeting(player, attacker))
                    return false;
            }
            return true;
        });
    }
}
