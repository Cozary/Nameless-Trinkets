package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import com.cozary.nameless_trinkets.items.trinkets.Woundbearer;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class WoundbearerEvents {

    @SubscribeEvent
    public static void savePlayerDamageIncrement(LivingDamageEvent.Pre event) {
        Woundbearer.Stats config = Woundbearer.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return;
            }
            var stack = accessories.getEquipped(ModItems.WOUNDBEARER.get());
            if (!stack.isEmpty() && !player.level().isClientSide) {
                float damageIncrement = event.getNewDamage() * (config.damageConversionPercentage/100);

                stack.getFirst().stack().set(ModDataComponents.WOUNDBEARER_DAMAGE.get(), stack.getFirst().stack().getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(),0).floatValue() + damageIncrement);
            }
        }
    }
}
