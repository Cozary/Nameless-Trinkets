package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ExperienceBattery;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;

public class ExperienceBatteryEvents {

    public static void register() {
        ModEvents.ExperienceDropModifierCallback.EVENT.register((entity, livingEntity) -> {
            ExperienceBattery.Stats config = ExperienceBattery.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return;
            }

            if (entity instanceof Player attackingPlayer) {

                var accessories = AccessoriesCapability.get(attackingPlayer);

                if (accessories == null) {
                    return;
                }
                var stack = accessories.getEquipped(ModItems.EXPERIENCE_BATTERY.get());
                if (stack.isEmpty() || livingEntity instanceof Player) {
                    return;
                }

                int originalExperience = livingEntity.getExperienceReward((ServerLevel) livingEntity.level(), entity);
                int bonusExperience = (int) (originalExperience * (1 - (config.extraExperiencePercentage / 100)));

                if (bonusExperience > 0) {
                    livingEntity.level().addFreshEntity(new ExperienceOrb((ServerLevel) livingEntity.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), bonusExperience));
                }
            }
        });
    }
}
