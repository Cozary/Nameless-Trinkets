package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ExperienceBattery;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.function.IntConsumer;

public class ExperienceBatteryHandler {

    public static void handleExperienceDrop(Player player, LivingEntity killedEntity, int originalExperience, IntConsumer experienceAdder) {
        ExperienceBattery.Stats config = ExperienceBattery.INSTANCE.getTrinketConfig();

        if (!config.isEnable || killedEntity instanceof Player || player.isSpectator())
            return;

        var accessories = AccessoriesCapability.get(player);
        if (accessories == null)
            return;

        var stack = accessories.getEquipped(ModItems.EXPERIENCE_BATTERY.get());
        if (stack.isEmpty())
            return;

        int bonusExperience = (int) (originalExperience * (config.extraExperiencePercentage / 100f));
        if (bonusExperience > 0) {
            experienceAdder.accept(bonusExperience);
        }
    }


}
