package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ExperienceBatteryEvents {

    @SubscribeEvent
    public static void handleExperienceDrop(LivingExperienceDropEvent event) {
        Player player = event.getAttackingPlayer();

        if (player == null) {
            return;
        }

        var stack = TrinketUtils.getEquippedTrinket(player, ModItems.EXPERIENCE_BATTERY.get());

        if (stack.isEmpty())
            return;

        int originalExperience = event.getOriginalExperience();

        ExperienceBatteryHandler.handleExperienceDrop(player, event.getEntity(), originalExperience, bonusExperience -> {
            event.setDroppedExperience(event.getDroppedExperience() + bonusExperience);
        });
    }


}