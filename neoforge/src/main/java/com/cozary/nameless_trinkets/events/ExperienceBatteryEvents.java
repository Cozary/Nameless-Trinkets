package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ExperienceBattery;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ExperienceBatteryEvents {

    @SubscribeEvent
    public static void handleExperienceDrop(LivingExperienceDropEvent event) {
        ExperienceBattery.Stats config = ExperienceBattery.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        Player attackingPlayer = event.getAttackingPlayer();
        if (attackingPlayer == null)
            return;

        var stack = AccessoriesCapability.get(attackingPlayer).getEquipped(ModItems.EXPERIENCE_BATTERY.get());
        if (stack.isEmpty() || event.getEntity() instanceof Player)
            return;


        float experienceMultiplier = config.experienceMultiplier;
        int originalExperience = event.getOriginalExperience();
        int bonusExperience = (int) (originalExperience * experienceMultiplier);

        if (bonusExperience > 0) {
            event.setDroppedExperience(event.getDroppedExperience() + bonusExperience);
        }
    }


}
