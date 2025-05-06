package com.cozary.nameless_trinkets.init;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.special.*;
import com.cozary.nameless_trinkets.items.trinkets.*;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {

    private static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, NamelessTrinkets.MOD_ID);

    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    //Trinkets
    public static final RegistryObject<Item> MISSING_PAGE = registerWithTab("missing_page", MissingPageBase::new);
    public static final RegistryObject<Item> REVERSE_CARD = registerWithTab("reverse_card", ReverseCardBase::new);
    public static final RegistryObject<Item> EXPERIENCE_BATTERY = registerWithTab("experience_battery", ExperienceBatteryBase::new);
    public static final RegistryObject<Item> BROKEN_ANKH = registerWithTab("broken_ankh", BrokenAnkhBase::new);
    public static final RegistryObject<Item> EXPERIENCE_MAGNET = registerWithTab("experience_magnet", ExperienceMagnetBase::new);
    public static final RegistryObject<Item> BROKEN_MAGNET = registerWithTab("broken_magnet", BrokenMagnetBase::new);
    public static final RegistryObject<Item> SUPER_MAGNET = registerWithTab("super_magnet", SuperMagnetBase::new);
    public static final RegistryObject<Item> WHAT_MAGNET = registerWithTab("what_magnet", WhatMagnetBase::new);
    public static final RegistryObject<Item> CALLUS = registerWithTab("callus", CallusBase::new);
    public static final RegistryObject<Item> SPEED_FORCE = registerWithTab("speed_force", SpeedForceBase::new);
    public static final RegistryObject<Item> VAMPIRE_BLOOD = registerWithTab("vampire_blood", VampireBloodBase::new);
    public static final RegistryObject<Item> LUCKY_ROCK = registerWithTab("lucky_rock", LuckyRockBase::new);
    public static final RegistryObject<Item> PUFFER_FISH_LIVER = registerWithTab("puffer_fish_liver", PufferFishLiverBase::new);
    public static final RegistryObject<Item> RAGE_MIND = registerWithTab("rage_mind", RageMindBase::new);
    public static final RegistryObject<Item> TICK = registerWithTab("tick", TickBase::new);
    public static final RegistryObject<Item> BLINDFOLD = registerWithTab("blindfold", BlindfoldBase::new);
    public static final RegistryObject<Item> EXPLOSION_PROOF_JACKET = registerWithTab("explosion_proof_jacket", ExplosionProofJacketBase::new);
    public static final RegistryObject<Item> CRACKED_CROWN = registerWithTab("cracked_crown", CrackedCrownBase::new);
    public static final RegistryObject<Item> GHAST_EYE = registerWithTab("ghast_eye", GhastEyeBase::new);
    public static final RegistryObject<Item> WOODEN_STICK = registerWithTab("wooden_stick", WoodenStickBase::new);
    public static final RegistryObject<Item> BLAZE_NUCLEUS = registerWithTab("blaze_nucleus", BlazeNucleusBase::new);
    public static final RegistryObject<Item> ICE_CUBE = registerWithTab("ice_cube", IceCubeBase::new);
    public static final RegistryObject<Item> SIGIL_OF_BAPHOMET = registerWithTab("sigil_of_baphomet", SigilOfBaphometBase::new);
    public static final RegistryObject<Item> CREEPER_SENSE = registerWithTab("creeper_sense", CreeperSenseBase::new);
    public static final RegistryObject<Item> FERTILIZER = registerWithTab("fertilizer", FertilizerBase::new);
    public static final RegistryObject<Item> GODS_CROWN = registerWithTab("gods_crown", GodsCrownBase::new);
    public static final RegistryObject<Item> AMPHIBIOUS_HANDS = registerWithTab("amphibious_hands", AmphibiousHandsBase::new);
    public static final RegistryObject<Item> GILLS = registerWithTab("gills", GillsBase::new);
    public static final RegistryObject<Item> MOON_STONE = registerWithTab("moon_stone", MoonStoneBase::new);
    public static final RegistryObject<Item> SLEEPING_PILLS = registerWithTab("sleeping_pills", SleepingPillsBase::new);
    public static final RegistryObject<Item> ETHEREAL_WINGS = registerWithTab("ethereal_wings", EtherealWingsBase::new);
    public static final RegistryObject<Item> SPIDER_LEGS = registerWithTab("spider_legs", SpiderLegsBase::new);
    public static final RegistryObject<Item> REFORGER = registerWithTab("reforger", ReforgerBase::new);
    public static final RegistryObject<Item> ELECTRIC_PADDLE = registerWithTab("electric_paddle", ElectricPaddleBase::new);
    public static final RegistryObject<Item> FRACTURED_NULLSTONE = registerWithTab("fractured_nullstone", FracturedNullstoneBase::new);
    public static final RegistryObject<Item> POCKET_LIGHTNING_ROD = registerWithTab("pocket_lightning_rod", PocketLightningRodBase::new);
    public static final RegistryObject<Item> FRAGILE_CLOUD = registerWithTab("fragile_cloud", FragileCloudBase::new);
    public static final RegistryObject<Item> SCARAB_AMULET = registerWithTab("scarab_amulet", ScarabAmuletBase::new);
    public static final RegistryObject<Item> FATE_EMERALD = registerWithTab("fate_emerald", FateEmeraldBase::new);
    public static final RegistryObject<Item> LIGHT_GLOVES = registerWithTab("light_gloves", LightGlovesBase::new);
    public static final RegistryObject<Item> DRAGONS_EYE = registerWithTab("dragons_eye", DragonsEyeBase::new);
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER = registerWithTab("four_leaf_clover", FourLeafCloverBase::new);
    public static final RegistryObject<Item> NELUMBO = registerWithTab("nelumbo", NelumboBase::new);
    public static final RegistryObject<Item> DARK_NELUMBO = registerWithTab("dark_nelumbo", DarkNelumboBase::new);
    public static final RegistryObject<Item> MINERS_SOUL = registerWithTab("miners_soul", MinersSoulBase::new);
    public static final RegistryObject<Item> TRUE_HEART_OF_THE_SEA = registerWithTab("true_heart_of_the_sea", TrueHeartOfTheSeaBase::new);
    public static final RegistryObject<Item> TEAR_OF_THE_SEA = registerWithTab("tear_of_the_sea", TearOfTheSeaBase::new);

    public static final RegistryObject<Item> SHRINKING_VEIL = registerWithTab("shrinking_veil", ShrinkingVeilBase::new);
    public static final RegistryObject<Item> TITANS_MARK = registerWithTab("titans_mark", TitansMarkBase::new);
    public static final RegistryObject<Item> WOUNDBEARER = registerWithTab("woundbearer", WoundbearerBase::new);
    public static final RegistryObject<Item> DYING_STAR = registerWithTab("dying_star", DyingStarBase::new);
    public static final RegistryObject<Item> RESONANT_HEART = registerWithTab("resonant_heart", ResonantHeartBase::new);

    public static final RegistryObject<Item> TRINKET_BUNDLE = registerWithTab("trinket_bundle", TrinketBundle::new);

    //Recycling
    public static final RegistryObject<Item> MYSTERIOUS_TRINKET = registerWithTab("mysterious_trinket", MysteriousTrinket::new);
    public static final RegistryObject<Item> UNKNOWN_FRAGMENT = registerWithTab("unknown_fragment", UnknownFragment::new);

    //Recipe Items
    public static final RegistryObject<Item> DUBIOUS_DUST = registerWithTab("dubious_dust", DubiousDust::new);
    public static final RegistryObject<Item> GLOWING_DUST = registerWithTab("glowing_dust", GlowingDust::new);
    public static final RegistryObject<Item> ULTIMATE_DUST = registerWithTab("ultimate_dust", UltimateDust::new);

    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<? extends Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static void loadClass() {
    }


}
