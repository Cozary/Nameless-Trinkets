package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.events.*;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.RegistryObject;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NamelessTrinketsFabric implements ModInitializer {

    private static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "nameless_trinkets"));


    @Override
    public void onInitialize() {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.nameless_trinkets"))
                .icon(() -> new ItemStack(ModItems.MYSTERIOUS_TRINKET.get()))
                .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
                .build()
        );


        NamelessTrinkets.init();

        NeoForgeConfigRegistry.INSTANCE.register(NamelessTrinkets.MOD_ID, ModConfig.Type.COMMON, ConfigurationHandler.spec);

        AmphibiousHandsEvents.register();
        BrokenAnkhEvents.register();
        FourLeafCloverEvents.register();
        GhastEyeEvents.register();
        LightGlovesEvents.register();
        LuckyRockEvents.register();
        MinersSoulEvents.register();
        SigilOfBaphometEvents.register();
        SleepingPillsEvents.register();
        TrueHeartOfTheSeaEvents.register();
        VampireBloodEvents.register();
        UnknownFragmentEvent.register();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin()) {
                for (ResourceKey<LootTable> lootTableKey : LOOT_TABLES_LIST) {
                    if (lootTableKey.equals(key)) {

                        LootTable.Builder lootTableBuilder = LootTable.lootTable();

                        for (ArrayList<Object> objects : tierList) {
                            double chance = (double) objects.get(0);
                            @SuppressWarnings("unchecked")
                            List<Item> items = (List<Item>) objects.get(1);

                            LootPool.Builder poolBuilder = LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1))
                                    .when(LootItemRandomChanceCondition.randomChance((float) chance));

                            for (Item item : items) {
                                poolBuilder.add(LootItem.lootTableItem(item));
                            }

                            lootTableBuilder.withPool(poolBuilder);
                            tableBuilder.pool(poolBuilder.build());
                        }
                    }
                }
            }
        });

        TrinketConfigs.loadClass();
    }

    public static final List<ResourceKey<LootTable>> LOOT_TABLES_LIST = List.of(
            BuiltInLootTables.SPAWN_BONUS_CHEST,
            BuiltInLootTables.END_CITY_TREASURE,
            BuiltInLootTables.SIMPLE_DUNGEON,
            BuiltInLootTables.VILLAGE_WEAPONSMITH,
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            BuiltInLootTables.VILLAGE_ARMORER,
            BuiltInLootTables.VILLAGE_CARTOGRAPHER,
            BuiltInLootTables.VILLAGE_MASON,
            BuiltInLootTables.VILLAGE_SHEPHERD,
            BuiltInLootTables.VILLAGE_BUTCHER,
            BuiltInLootTables.VILLAGE_FLETCHER,
            BuiltInLootTables.VILLAGE_FISHER,
            BuiltInLootTables.VILLAGE_TANNERY,
            BuiltInLootTables.VILLAGE_TEMPLE,
            BuiltInLootTables.VILLAGE_DESERT_HOUSE,
            BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
            BuiltInLootTables.VILLAGE_TAIGA_HOUSE,
            BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
            BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.NETHER_BRIDGE,
            BuiltInLootTables.STRONGHOLD_LIBRARY,
            BuiltInLootTables.STRONGHOLD_CROSSING,
            BuiltInLootTables.STRONGHOLD_CORRIDOR,
            BuiltInLootTables.DESERT_PYRAMID,
            BuiltInLootTables.JUNGLE_TEMPLE,
            BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER,
            BuiltInLootTables.IGLOO_CHEST,
            BuiltInLootTables.WOODLAND_MANSION,
            BuiltInLootTables.UNDERWATER_RUIN_SMALL,
            BuiltInLootTables.UNDERWATER_RUIN_BIG,
            BuiltInLootTables.BURIED_TREASURE,
            BuiltInLootTables.SHIPWRECK_MAP,
            BuiltInLootTables.SHIPWRECK_SUPPLY,
            BuiltInLootTables.SHIPWRECK_TREASURE,
            BuiltInLootTables.PILLAGER_OUTPOST,
            BuiltInLootTables.BASTION_TREASURE,
            BuiltInLootTables.BASTION_OTHER,
            BuiltInLootTables.BASTION_BRIDGE,
            BuiltInLootTables.BASTION_HOGLIN_STABLE,
            BuiltInLootTables.ANCIENT_CITY,
            BuiltInLootTables.ANCIENT_CITY_ICE_BOX,
            BuiltInLootTables.RUINED_PORTAL,
            BuiltInLootTables.CAT_MORNING_GIFT
    );

    ArrayList<ArrayList<Object>> tierList = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(0.01, Arrays.asList(
                    ModItems.GODS_CROWN.get()
            ))),
            new ArrayList<>(Arrays.asList(0.025, Arrays.asList(
                    ModItems.REVERSE_CARD.get(),
                    ModItems.BROKEN_ANKH.get(),
                    ModItems.RAGE_MIND.get(),
                    ModItems.CRACKED_CROWN.get(),
                    ModItems.SIGIL_OF_BAPHOMET.get(),
                    ModItems.TRUE_HEART_OF_THE_SEA.get(),
                    ModItems.REFORGER.get(),
                    ModItems.FATE_EMERALD.get(),
                    ModItems.LIGHT_GLOVES.get(),
                    ModItems.FOUR_LEAF_CLOVER.get(),
                    ModItems.MINERS_SOUL.get()
            ))),
            new ArrayList<>(Arrays.asList(0.05, Arrays.asList(
                    ModItems.MISSING_PAGE.get(),
                    ModItems.EXPERIENCE_BATTERY.get(),
                    ModItems.SUPER_MAGNET.get(),
                    ModItems.WHAT_MAGNET.get(),
                    ModItems.CALLUS.get(),
                    ModItems.SPEED_FORCE.get(),
                    ModItems.VAMPIRE_BLOOD.get(),
                    ModItems.TICK.get(),
                    ModItems.GHAST_EYE.get(),
                    ModItems.FERTILIZER.get(),
                    ModItems.TEAR_OF_THE_SEA.get(),
                    ModItems.ETHEREAL_WINGS.get(),
                    ModItems.ELECTRIC_PADDLE.get(),
                    ModItems.FRACTURED_NULLSTONE.get(),
                    ModItems.FRAGILE_CLOUD.get(),
                    ModItems.SCARAB_AMULET.get()
            ))),
            new ArrayList<>(Arrays.asList(0.1, Arrays.asList(
                    ModItems.EXPERIENCE_MAGNET.get(),
                    ModItems.BROKEN_MAGNET.get(),
                    ModItems.LUCKY_ROCK.get(),
                    ModItems.PUFFER_FISH_LIVER.get(),
                    ModItems.BLINDFOLD.get(),
                    ModItems.EXPLOSION_PROOF_JACKET.get(),
                    ModItems.WOODEN_STICK.get(),
                    ModItems.BLAZE_NUCLEUS.get(),
                    ModItems.ICE_CUBE.get(),
                    ModItems.CREEPER_SENSE.get(),
                    ModItems.AMPHIBIOUS_HANDS.get(),
                    ModItems.GILLS.get(),
                    ModItems.MOON_STONE.get(),
                    ModItems.SLEEPING_PILLS.get(),
                    ModItems.NELUMBO.get(),
                    ModItems.DARK_NELUMBO.get(),
                    ModItems.SPIDER_LEGS.get(),
                    ModItems.DRAGONS_EYE.get(),
                    ModItems.POCKET_LIGHTNING_ROD.get()
            )))
    ));
}
