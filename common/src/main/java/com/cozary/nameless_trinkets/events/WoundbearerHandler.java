package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.items.trinkets.WoundbearerBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WoundbearerHandler {
    public static final Map<UUID, Integer> LAST_DAMAGE_TICKS = new ConcurrentHashMap<>();

    public static void savePlayerDamageIncrement(Player player, float damageAmount, ItemStack stack) {
        WoundbearerBase.Stats config = WoundbearerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;


        if (!player.level().isClientSide) {
            float damageIncrement = damageAmount * (config.damageConversionPercentage / 100);

            float currentDamage = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0f);

            float newDamage = currentDamage + damageIncrement;
            if (newDamage > config.maxDamageLimit) {
                newDamage = config.maxDamageLimit;
            }
            if (Float.isInfinite(newDamage) || newDamage > Float.MAX_VALUE) {
                newDamage = Float.MAX_VALUE;
            }

            stack.set(ModDataComponents.WOUNDBEARER_DAMAGE.get(), newDamage);
            LAST_DAMAGE_TICKS.put(player.getUUID(), player.tickCount);
        }
    }
}
