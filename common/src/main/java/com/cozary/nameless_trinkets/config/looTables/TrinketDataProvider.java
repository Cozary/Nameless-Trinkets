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
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.NETHER_BRIDGE,
            BuiltInLootTables.STRONGHOLD_LIBRARY,
            BuiltInLootTables.STRONGHOLD_CROSSING,
            BuiltInLootTables.STRONGHOLD_CORRIDOR,
            BuiltInLootTables.DESERT_PYRAMID,
            BuiltInLootTables.JUNGLE_TEMPLE,
            BuiltInLootTables.IGLOO_CHEST,
            BuiltInLootTables.WOODLAND_MANSION,
            BuiltInLootTables.UNDERWATER_RUIN_BIG,
            BuiltInLootTables.BURIED_TREASURE,
            BuiltInLootTables.SHIPWRECK_TREASURE,
            BuiltInLootTables.PILLAGER_OUTPOST,
            BuiltInLootTables.BASTION_TREASURE,
            BuiltInLootTables.BASTION_BRIDGE,
            BuiltInLootTables.BASTION_HOGLIN_STABLE,
            BuiltInLootTables.ANCIENT_CITY,
            BuiltInLootTables.RUINED_PORTAL,
            BuiltInLootTables.CAT_MORNING_GIFT
    );
    // Tier chances
    private static final double CHANCE_COMMON = 0.01;
    private static final double CHANCE_RARE = 0.005;
    private static final double CHANCE_LEGEND = 0.0025;
    private static final double CHANCE_MYTHIC = 0.0;

    public static List<List<Object>> getTrinketList() {
        return new ArrayList<>(Arrays.asList(
                List.of(CHANCE_MYTHIC, ModItems.GODS_CROWN.get()),
                List.of(CHANCE_MYTHIC, ModItems.DYING_STAR.get()),
                List.of(CHANCE_LEGEND, ModItems.REVERSE_CARD.get()),
                List.of(CHANCE_LEGEND, ModItems.BROKEN_ANKH.get()),
                List.of(CHANCE_LEGEND, ModItems.RAGE_MIND.get()),
                List.of(CHANCE_LEGEND, ModItems.CRACKED_CROWN.get()),
                List.of(CHANCE_LEGEND, ModItems.SIGIL_OF_BAPHOMET.get()),
                List.of(CHANCE_LEGEND, ModItems.TRUE_HEART_OF_THE_SEA.get()),
                List.of(CHANCE_LEGEND, ModItems.REFORGER.get()),
                List.of(CHANCE_LEGEND, ModItems.FATE_EMERALD.get()),
                List.of(CHANCE_LEGEND, ModItems.LIGHT_GLOVES.get()),
                List.of(CHANCE_LEGEND, ModItems.FOUR_LEAF_CLOVER.get()),
                List.of(CHANCE_LEGEND, ModItems.MINERS_SOUL.get()),
                List.of(CHANCE_RARE, ModItems.MISSING_PAGE.get()),
                List.of(CHANCE_RARE, ModItems.EXPERIENCE_BATTERY.get()),
                List.of(CHANCE_RARE, ModItems.SUPER_MAGNET.get()),
                List.of(CHANCE_RARE, ModItems.WHAT_MAGNET.get()),
                List.of(CHANCE_RARE, ModItems.CALLUS.get()),
                List.of(CHANCE_RARE, ModItems.SPEED_FORCE.get()),
                List.of(CHANCE_RARE, ModItems.VAMPIRE_BLOOD.get()),
                List.of(CHANCE_RARE, ModItems.TICK.get()),
                List.of(CHANCE_RARE, ModItems.GHAST_EYE.get()),
                List.of(CHANCE_RARE, ModItems.FERTILIZER.get()),
                List.of(CHANCE_RARE, ModItems.TEAR_OF_THE_SEA.get()),
                List.of(CHANCE_RARE, ModItems.ETHEREAL_WINGS.get()),
                List.of(CHANCE_RARE, ModItems.ELECTRIC_PADDLE.get()),
                List.of(CHANCE_RARE, ModItems.FRACTURED_NULLSTONE.get()),
                List.of(CHANCE_RARE, ModItems.FRAGILE_CLOUD.get()),
                List.of(CHANCE_RARE, ModItems.SCARAB_AMULET.get()),
                List.of(CHANCE_RARE, ModItems.RESONANT_HEART.get()),
                List.of(CHANCE_COMMON, ModItems.EXPERIENCE_MAGNET.get()),
                List.of(CHANCE_COMMON, ModItems.BROKEN_MAGNET.get()),
                List.of(CHANCE_COMMON, ModItems.LUCKY_ROCK.get()),
                List.of(CHANCE_COMMON, ModItems.PUFFER_FISH_LIVER.get()),
                List.of(CHANCE_COMMON, ModItems.BLINDFOLD.get()),
                List.of(CHANCE_COMMON, ModItems.EXPLOSION_PROOF_JACKET.get()),
                List.of(CHANCE_COMMON, ModItems.WOODEN_STICK.get()),
                List.of(CHANCE_COMMON, ModItems.BLAZE_NUCLEUS.get()),
                List.of(CHANCE_COMMON, ModItems.ICE_CUBE.get()),
                List.of(CHANCE_COMMON, ModItems.CREEPER_SENSE.get()),
                List.of(CHANCE_COMMON, ModItems.AMPHIBIOUS_HANDS.get()),
                List.of(CHANCE_COMMON, ModItems.GILLS.get()),
                List.of(CHANCE_COMMON, ModItems.MOON_STONE.get()),
                List.of(CHANCE_COMMON, ModItems.SLEEPING_PILLS.get()),
                List.of(CHANCE_COMMON, ModItems.NELUMBO.get()),
                List.of(CHANCE_COMMON, ModItems.DARK_NELUMBO.get()),
                List.of(CHANCE_COMMON, ModItems.SPIDER_LEGS.get()),
                List.of(CHANCE_COMMON, ModItems.DRAGONS_EYE.get()),
                List.of(CHANCE_COMMON, ModItems.POCKET_LIGHTNING_ROD.get()),
                List.of(CHANCE_COMMON, ModItems.SHRINKING_VEIL.get()),
                List.of(CHANCE_COMMON, ModItems.TITANS_MARK.get()),
                List.of(CHANCE_COMMON, ModItems.WOUNDBEARER.get())
        ));
    }
}
