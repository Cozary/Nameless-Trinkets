package com.cozary.nameless_trinkets.config.looTables;

import com.cozary.nameless_trinkets.init.ModItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrinketDataProvider {

    // Tier chances (per loot roll)
    private static final double CHANCE_COMMON   = 0.001;   // ~1 per 17–34 chests (typical 3–6 rolls)
    private static final double CHANCE_UNCOMMON = 0.0005;  // ~1 per 34–67 chests
    private static final double CHANCE_RARE     = 0.0002;  // ~1 per 100–170 chests
    private static final double CHANCE_LEGEND   = 0.00005; // ~1 per 500–1000 chests
    private static final double CHANCE_MYTHIC   = 0.00001; // ~1 per 2000–3000 chests

    /**
     * Default loot tables used ONLY when configs are missing / generated.
     * Keep this list "artifact-themed" and avoid villages to enforce rarity + theming by default.
     */
    public static final List<ResourceKey<LootTable>> LOOT_TABLES = List.of(
            BuiltInLootTables.SPAWN_BONUS_CHEST,
            BuiltInLootTables.SIMPLE_DUNGEON,
            BuiltInLootTables.ABANDONED_MINESHAFT,

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
            BuiltInLootTables.RUINED_PORTAL,

            BuiltInLootTables.NETHER_BRIDGE,
            BuiltInLootTables.BASTION_TREASURE,
            BuiltInLootTables.BASTION_OTHER,
            BuiltInLootTables.BASTION_BRIDGE,
            BuiltInLootTables.BASTION_HOGLIN_STABLE,

            BuiltInLootTables.END_CITY_TREASURE,

            BuiltInLootTables.ANCIENT_CITY,
            BuiltInLootTables.ANCIENT_CITY_ICE_BOX,

            BuiltInLootTables.CAT_MORNING_GIFT
    );

    /**
     * Default list used when generating configs.
     * This does NOT control theming directly; theming happens via per-item config loot table lists.
     * But this sets sane default rarity tiers if configs are missing.
     */
    public static List<List<Object>> getTrinketList() {
        return new ArrayList<>(Arrays.asList(

                // ─────────────────────────────
                // Mythic (rarest)
                // ─────────────────────────────
                List.of(CHANCE_MYTHIC, ModItems.GODS_CROWN.get()),
                List.of(CHANCE_MYTHIC, ModItems.DYING_STAR.get()),

                // ─────────────────────────────
                // Legendary
                // ─────────────────────────────
                List.of(CHANCE_LEGEND, ModItems.DRAGONS_EYE.get()),
                List.of(CHANCE_LEGEND, ModItems.TRUE_HEART_OF_THE_SEA.get()),
                List.of(CHANCE_LEGEND, ModItems.BLAZE_NUCLEUS.get()),
                List.of(CHANCE_LEGEND, ModItems.SIGIL_OF_BAPHOMET.get()),
                List.of(CHANCE_LEGEND, ModItems.MINERS_SOUL.get()),
                List.of(CHANCE_LEGEND, ModItems.BROKEN_ANKH.get()),

                // ─────────────────────────────
                // Rare
                // ─────────────────────────────
                List.of(CHANCE_RARE, ModItems.RESONANT_HEART.get()),
                List.of(CHANCE_RARE, ModItems.REVERSE_CARD.get()),
                List.of(CHANCE_RARE, ModItems.REFORGER.get()),
                List.of(CHANCE_RARE, ModItems.CRACKED_CROWN.get()),
                List.of(CHANCE_RARE, ModItems.RAGE_MIND.get()),
                List.of(CHANCE_RARE, ModItems.SPEED_FORCE.get()),
                List.of(CHANCE_RARE, ModItems.LUCKY_ROCK.get()),
                List.of(CHANCE_RARE, ModItems.SCARAB_AMULET.get()),
                List.of(CHANCE_RARE, ModItems.TEAR_OF_THE_SEA.get()),
                List.of(CHANCE_RARE, ModItems.SHRINKING_VEIL.get()),
                List.of(CHANCE_RARE, ModItems.ETHEREAL_WINGS.get()),
                List.of(CHANCE_RARE, ModItems.VAMPIRE_BLOOD.get()),
                List.of(CHANCE_RARE, ModItems.CREEPER_SENSE.get()),
                List.of(CHANCE_RARE, ModItems.WOUNDBEARER.get()),

                // ─────────────────────────────
                // Uncommon
                // ─────────────────────────────
                List.of(CHANCE_UNCOMMON, ModItems.FOUR_LEAF_CLOVER.get()),
                List.of(CHANCE_UNCOMMON, ModItems.FATE_EMERALD.get()),
                List.of(CHANCE_UNCOMMON, ModItems.LIGHT_GLOVES.get()),
                List.of(CHANCE_UNCOMMON, ModItems.ELECTRIC_PADDLE.get()),
                List.of(CHANCE_UNCOMMON, ModItems.CALLUS.get()),
                List.of(CHANCE_UNCOMMON, ModItems.SLEEPING_PILLS.get()),
                List.of(CHANCE_UNCOMMON, ModItems.ICE_CUBE.get()),
                List.of(CHANCE_UNCOMMON, ModItems.GILLS.get()),
                List.of(CHANCE_UNCOMMON, ModItems.NELUMBO.get()),
                List.of(CHANCE_UNCOMMON, ModItems.DARK_NELUMBO.get()),

                // ─────────────────────────────
                // Common (still "rare" in practice)
                // ─────────────────────────────
                List.of(CHANCE_COMMON, ModItems.EXPERIENCE_MAGNET.get()),
                List.of(CHANCE_COMMON, ModItems.EXPERIENCE_BATTERY.get()),
                List.of(CHANCE_COMMON, ModItems.SUPER_MAGNET.get()),
                List.of(CHANCE_COMMON, ModItems.WHAT_MAGNET.get()),
                List.of(CHANCE_COMMON, ModItems.BROKEN_MAGNET.get()),
                List.of(CHANCE_COMMON, ModItems.FERTILIZER.get()),
                List.of(CHANCE_COMMON, ModItems.FRAGILE_CLOUD.get()),
                List.of(CHANCE_COMMON, ModItems.FRACTURED_NULLSTONE.get()),
                List.of(CHANCE_COMMON, ModItems.PUFFER_FISH_LIVER.get()),
                List.of(CHANCE_COMMON, ModItems.BLINDFOLD.get()),
                List.of(CHANCE_COMMON, ModItems.EXPLOSION_PROOF_JACKET.get()),
                List.of(CHANCE_COMMON, ModItems.WOODEN_STICK.get()),
                List.of(CHANCE_COMMON, ModItems.AMPHIBIOUS_HANDS.get()),
                List.of(CHANCE_COMMON, ModItems.MOON_STONE.get()),
                List.of(CHANCE_COMMON, ModItems.SPIDER_LEGS.get()),
                List.of(CHANCE_COMMON, ModItems.POCKET_LIGHTNING_ROD.get()),
                List.of(CHANCE_COMMON, ModItems.TITANS_MARK.get()),
                List.of(CHANCE_COMMON, ModItems.MISSING_PAGE.get())
        ));
    }
}
