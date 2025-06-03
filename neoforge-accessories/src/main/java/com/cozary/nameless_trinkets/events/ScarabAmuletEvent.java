package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ScarabAmulet;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ScarabAmuletEvent {

    @SubscribeEvent
    public static void preventHuskTargeting(LivingChangeTargetEvent event) {

        if (event.getOriginalAboutToBeSetTarget() instanceof Player player) {

            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.SCARAB_AMULET.get());

            if (stack.isEmpty())
                return;

            if(ScarabAmuletHandler.shouldPreventHuskTargeting(player, event.getEntity())){
                event.setNewAboutToBeSetTarget(null);

            }

        }
    }

}
