package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;

public class ExperienceBatteryEvents {

    public static void register() {
        ModEvents.ExperienceDropModifierCallback.EVENT.register((entity, livingEntity) -> {
            if (livingEntity == null) return;
            if (entity instanceof Player player) {
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    int originalExperience = livingEntity.getExperienceReward(serverLevel, player);

                    var stack = TrinketUtils.getEquippedTrinket(player, ModItems.EXPERIENCE_BATTERY.get());

                    if (stack.isEmpty())
                        return;

                    ExperienceBatteryHandler.handleExperienceDrop(player, livingEntity, originalExperience, bonusExperience -> {
                        serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), bonusExperience));
                    });
                }
            }
        });

    }
}
