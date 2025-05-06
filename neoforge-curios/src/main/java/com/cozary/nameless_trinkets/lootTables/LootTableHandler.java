package com.cozary.nameless_trinkets.lootTables;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.config.looTables.TrinketDataProvider;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfig;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.init.ModItemsCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.Optional;
import java.util.function.Supplier;

import static com.cozary.nameless_trinkets.config.TrinketConfigs.getItemName;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class LootTableHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation tableId = event.getName();

        for (ResourceKey<LootTable> lootTableKey : TrinketDataProvider.LOOT_TABLES) {
            if (lootTableKey.location().equals(tableId))
                return;

            for (TrinketLootConfig config : TrinketLootConfigsManager.getConfigs()) {

                if (!config.getLootTables().contains(tableId))
                    return;

                Optional<Supplier<Item>> optionalItem = ModItemsCurios.CREATIVE_TAB_ITEMS.stream()
                        .filter(item -> getItemName((TrinketItem<?>) item.get()).equals(config.getItemId()))
                        .findFirst();

                if (optionalItem.isEmpty())
                    return;

                Item item = optionalItem.get().get();
                float chance = (float) config.getChance();

                String poolName = "nameless_trinkets_pool_" + BuiltInRegistries.ITEM.getKey(item).getPath();
                if (event.getTable().getPool(poolName) != null)
                    return;

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

}
