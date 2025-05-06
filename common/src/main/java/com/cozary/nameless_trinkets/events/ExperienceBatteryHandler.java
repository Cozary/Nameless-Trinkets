package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.ExperienceBatteryBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.function.IntConsumer;

public class ExperienceBatteryHandler {

    public static void handleExperienceDrop(Player player, LivingEntity killedEntity, int originalExperience, IntConsumer experienceAdder) {
        ExperienceBatteryBase.Stats config = ExperienceBatteryBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable || killedEntity instanceof Player || player.isSpectator())
            return;


        int bonusExperience = (int) (originalExperience * (config.extraExperiencePercentage / 100f));
        if (bonusExperience > 0) {
            experienceAdder.accept(bonusExperience);
        }
    }


}
