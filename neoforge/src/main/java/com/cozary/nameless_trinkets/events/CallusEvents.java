package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.Callus;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class CallusEvents {

    @SubscribeEvent
    public static void applyCallusDamageReduction(LivingDamageEvent.Pre event) {
        Callus.Stats config = Callus.INSTANCE.getTrinketConfig();

        if (!config.isEnable || !(event.getEntity() instanceof Player player))
            return;

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.CALLUS.get());
        if (stack.isEmpty())
            return;

        var source = event.getSource();
        float newDamage = event.getOriginalDamage();

        if (isNullifiedDamageType(source)) {
            newDamage = 0;
        } else if (source.is(DamageTypes.FALL)) {
            newDamage *= (float) (1 - (config.fallDamageReductionPercentage / 100.0));
        } else {
            newDamage *= (float) (1 - (config.generalDamageReductionPercentage / 100.0));
        }

        event.setNewDamage(newDamage);
    }

    private static boolean isNullifiedDamageType(DamageSource source) {
        return source.is(DamageTypes.CACTUS) ||
                source.is(DamageTypes.FALLING_ANVIL) ||
                source.is(DamageTypes.HOT_FLOOR) ||
                source.is(DamageTypes.SWEET_BERRY_BUSH);
    }


}
