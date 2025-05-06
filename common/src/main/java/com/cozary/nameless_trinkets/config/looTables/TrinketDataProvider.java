package com.cozary.nameless_trinkets.config.looTables;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private static final Map<String, Double> ITEM_PROBABILITIES = Map.ofEntries(
            Map.entry("gods_crown", 0.0),
            Map.entry("dying_star", 0.0),
            Map.entry("reverse_card", 0.0025),
            Map.entry("broken_ankh", 0.0025),
            Map.entry("rage_mind", 0.0025),
            Map.entry("cracked_crown", 0.0025),
            Map.entry("sigil_of_baphomet", 0.0025),
            Map.entry("true_heart_of_the_sea", 0.0025),
            Map.entry("reforger", 0.0025),
            Map.entry("fate_emerald", 0.0025),
            Map.entry("light_gloves", 0.0025),
            Map.entry("four_leaf_clover", 0.0025),
            Map.entry("miners_soul", 0.0025),
            Map.entry("missing_page", 0.005),
            Map.entry("experience_battery", 0.005),
            Map.entry("super_magnet", 0.005),
            Map.entry("what_magnet", 0.005),
            Map.entry("callus", 0.005),
            Map.entry("speed_force", 0.005),
            Map.entry("vampire_blood", 0.005),
            Map.entry("tick", 0.005),
            Map.entry("ghast_eye", 0.005),
            Map.entry("fertilizer", 0.005),
            Map.entry("tear_of_the_sea", 0.005),
            Map.entry("ethereal_wings", 0.005),
            Map.entry("electric_paddle", 0.005),
            Map.entry("fractured_nullstone", 0.005),
            Map.entry("fragile_cloud", 0.005),
            Map.entry("scarab_amulet", 0.005),
            Map.entry("resonant_heart", 0.005),
            Map.entry("experience_magnet", 0.01),
            Map.entry("broken_magnet", 0.01),
            Map.entry("lucky_rock", 0.01),
            Map.entry("puffer_fish_liver", 0.01),
            Map.entry("blindfold", 0.01),
            Map.entry("explosion_proof_jacket", 0.01),
            Map.entry("wooden_stick", 0.01),
            Map.entry("blaze_nucleus", 0.01),
            Map.entry("ice_cube", 0.01),
            Map.entry("creeper_sense", 0.01),
            Map.entry("amphibious_hands", 0.01),
            Map.entry("gills", 0.01),
            Map.entry("moon_stone", 0.01),
            Map.entry("sleeping_pills", 0.01),
            Map.entry("nelumbo", 0.01),
            Map.entry("dark_nelumbo", 0.01),
            Map.entry("spider_legs", 0.01),
            Map.entry("dragons_eye", 0.01),
            Map.entry("pocket_lightning_rod", 0.01),
            Map.entry("shrinking_veil", 0.01),
            Map.entry("titans_mark", 0.01),
            Map.entry("woundbearer", 0.01)
    );

    public static List<List<Object>> getTrinketList() {
        List<List<Object>> list = new ArrayList<>();

        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);

            if (id != null && id.getNamespace().equals("nameless_trinkets") && item instanceof TrinketItem<?>) {
                double probability = ITEM_PROBABILITIES.getOrDefault(id.getPath(), 0.01);
                list.add(List.of(probability, item));
            }
        }

        return list;
    }


 /*   public static List<List<Object>> getTrinketList() {
        return new ArrayList<>(Arrays.asList(
                List.of(0.0, ModItems.GODS_CROWN.get()),
                List.of(0.0, ModItems.DYING_STAR.get()),
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
    }*/
}
