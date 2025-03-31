package com.cozary.nameless_trinkets.config.looTables;

import com.cozary.nameless_trinkets.init.ModItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrinketDataProvider {

    public static final List<ResourceKey<LootTable>> LOOT_TABLES = List.of(
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

    public static List<List<Object>> getTrinketList() {
        return new ArrayList<>(Arrays.asList(
                //List.of(0.001, ModItems.GODS_CROWN.get()),
                //List.of(0.001, ModItems.DYING_STAR.get()),
                List.of(0.0025, ModItems.REVERSE_CARD.get()),
                List.of(0.0025, ModItems.BROKEN_ANKH.get()),
                List.of(0.0025, ModItems.RAGE_MIND.get()),
                List.of(0.0025, ModItems.CRACKED_CROWN.get()),
                List.of(0.0025, ModItems.SIGIL_OF_BAPHOMET.get()),
                List.of(0.0025, ModItems.TRUE_HEART_OF_THE_SEA.get()),
                List.of(0.0025, ModItems.REFORGER.get()),
                List.of(0.0025, ModItems.FATE_EMERALD.get()),
                List.of(0.0025, ModItems.LIGHT_GLOVES.get()),
                List.of(0.0025, ModItems.FOUR_LEAF_CLOVER.get()),
                List.of(0.0025, ModItems.MINERS_SOUL.get()),
                List.of(0.005, ModItems.MISSING_PAGE.get()),
                List.of(0.005, ModItems.EXPERIENCE_BATTERY.get()),
                List.of(0.005, ModItems.SUPER_MAGNET.get()),
                List.of(0.005, ModItems.WHAT_MAGNET.get()),
                List.of(0.005, ModItems.CALLUS.get()),
                List.of(0.005, ModItems.SPEED_FORCE.get()),
                List.of(0.005, ModItems.VAMPIRE_BLOOD.get()),
                List.of(0.005, ModItems.TICK.get()),
                List.of(0.005, ModItems.GHAST_EYE.get()),
                List.of(0.005, ModItems.FERTILIZER.get()),
                List.of(0.005, ModItems.TEAR_OF_THE_SEA.get()),
                List.of(0.005, ModItems.ETHEREAL_WINGS.get()),
                List.of(0.005, ModItems.ELECTRIC_PADDLE.get()),
                List.of(0.005, ModItems.FRACTURED_NULLSTONE.get()),
                List.of(0.005, ModItems.FRAGILE_CLOUD.get()),
                List.of(0.005, ModItems.SCARAB_AMULET.get()),
                List.of(0.005, ModItems.RESONANT_HEART.get()),
                List.of(0.01, ModItems.EXPERIENCE_MAGNET.get()),
                List.of(0.01, ModItems.BROKEN_MAGNET.get()),
                List.of(0.01, ModItems.LUCKY_ROCK.get()),
                List.of(0.01, ModItems.PUFFER_FISH_LIVER.get()),
                List.of(0.01, ModItems.BLINDFOLD.get()),
                List.of(0.01, ModItems.EXPLOSION_PROOF_JACKET.get()),
                List.of(0.01, ModItems.WOODEN_STICK.get()),
                List.of(0.01, ModItems.BLAZE_NUCLEUS.get()),
                List.of(0.01, ModItems.ICE_CUBE.get()),
                List.of(0.01, ModItems.CREEPER_SENSE.get()),
                List.of(0.01, ModItems.AMPHIBIOUS_HANDS.get()),
                List.of(0.01, ModItems.GILLS.get()),
                List.of(0.01, ModItems.MOON_STONE.get()),
                List.of(0.01, ModItems.SLEEPING_PILLS.get()),
                List.of(0.01, ModItems.NELUMBO.get()),
                List.of(0.01, ModItems.DARK_NELUMBO.get()),
                List.of(0.01, ModItems.SPIDER_LEGS.get()),
                List.of(0.01, ModItems.DRAGONS_EYE.get()),
                List.of(0.01, ModItems.POCKET_LIGHTNING_ROD.get()),
                List.of(0.01, ModItems.SHRINKING_VEIL.get()),
                List.of(0.01, ModItems.TITANS_MARK.get()),
                List.of(0.01, ModItems.WOUNDBEARER.get())
        ));
    }
}
