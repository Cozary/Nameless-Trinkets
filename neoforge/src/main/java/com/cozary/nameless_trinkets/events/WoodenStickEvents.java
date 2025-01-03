package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.WoodenStick;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class WoodenStickEvents {

    @SubscribeEvent
    public static void cancelWoodenStick(LivingDamageEvent.Pre event) {
        WoodenStick.Stats config = WoodenStick.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (event.getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.WOODEN_STICK.get());

            if (!stack.isEmpty() && !player.level().isClientSide) {
                if (!player.getCooldowns().isOnCooldown(stack.getFirst().stack().getItem())) {
                    event.setNewDamage(0);
                    player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), (int) config.cooldown);
                }
            }
        }
    }
}
