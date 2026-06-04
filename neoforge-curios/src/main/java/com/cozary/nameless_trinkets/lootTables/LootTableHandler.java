package com.cozary.nameless_trinkets.lootTables;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfig;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.RegistryObject;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.Optional;

import static com.cozary.nameless_trinkets.config.TrinketConfigs.getItemName;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LootTableHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        Identifier tableId = event.getName();

        for (TrinketLootConfig config : TrinketLootConfigsManager.getConfigs()) {

            if (!config.getLootTables().contains(tableId))
                continue;

            Optional<RegistryObject<Item>> optionalItem = ModItems.CREATIVE_TAB_ITEMS.stream()
                    .filter(item -> getItemName((TrinketItem<?>) item.get()).equals(config.getItemId()))
                    .findFirst();

            if (optionalItem.isEmpty())
                continue;

            Item item = optionalItem.get().get();
            
            double baseChance = config.getChance();
            double multiplier = CommonConfigManager.getConfig().getGlobalLootMultiplier();

            // Apply multiplier
            double scaledChance = baseChance * (1.0 + multiplier);

            // Clamp to [0.0, 1.0]
            scaledChance = Math.max(0.0, Math.min(1.0, scaledChance));

            // Final value used by loot table
            float chance = (float) scaledChance;

            String poolName = "nameless_trinkets_pool_" + BuiltInRegistries.ITEM.getKey(item).getPath();
            if (event.getTable().getPool(poolName) != null)
                continue;

            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(chance))
                    .add(LootItem.lootTableItem(item))
                    .name(poolName)
                    .build();

            event.getTable().addPool(pool);
        }
    }

}
