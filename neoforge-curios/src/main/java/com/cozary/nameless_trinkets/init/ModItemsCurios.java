package com.cozary.nameless_trinkets.init;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.special.*;
import com.cozary.nameless_trinkets.items.trinkets.*;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItemsCurios {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, NamelessTrinkets.MOD_ID);

    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    //Trinkets
    public static final Supplier<Item> MISSING_PAGE = registerWithTab("missing_page", MissingPageBase::new);
    public static final Supplier<Item> REVERSE_CARD = registerWithTab("reverse_card", ReverseCardBase::new);
    public static final Supplier<Item> EXPERIENCE_BATTERY = registerWithTab("experience_battery", ExperienceBatteryBase::new);
    public static final Supplier<Item> BROKEN_ANKH = registerWithTab("broken_ankh", BrokenAnkhBase::new);
    public static final Supplier<Item> EXPERIENCE_MAGNET = registerWithTab("experience_magnet", ExperienceMagnetBase::new);
    public static final Supplier<Item> BROKEN_MAGNET = registerWithTab("broken_magnet", BrokenMagnetBase::new);
    public static final Supplier<Item> SUPER_MAGNET = registerWithTab("super_magnet", SuperMagnetBase::new);
    public static final Supplier<Item> WHAT_MAGNET = registerWithTab("what_magnet", WhatMagnetBase::new);
    public static final Supplier<Item> CALLUS = registerWithTab("callus", CallusBase::new);
    public static final Supplier<Item> SPEED_FORCE = registerWithTab("speed_force", SpeedForceBase::new);
    public static final Supplier<Item> VAMPIRE_BLOOD = registerWithTab("vampire_blood", VampireBloodBase::new);
    public static final Supplier<Item> LUCKY_ROCK = registerWithTab("lucky_rock", LuckyRockBase::new);
    public static final Supplier<Item> PUFFER_FISH_LIVER = registerWithTab("puffer_fish_liver", PufferFishLiverBase::new);
    public static final Supplier<Item> RAGE_MIND = registerWithTab("rage_mind", RageMindBase::new);
    public static final Supplier<Item> TICK = registerWithTab("tick", TickBase::new);
    public static final Supplier<Item> BLINDFOLD = registerWithTab("blindfold", BlindfoldBase::new);
    public static final Supplier<Item> EXPLOSION_PROOF_JACKET = registerWithTab("explosion_proof_jacket", ExplosionProofJacketBase::new);
    public static final Supplier<Item> CRACKED_CROWN = registerWithTab("cracked_crown", CrackedCrownBase::new);
    public static final Supplier<Item> GHAST_EYE = registerWithTab("ghast_eye", GhastEyeBase::new);
    public static final Supplier<Item> WOODEN_STICK = registerWithTab("wooden_stick", WoodenStickBase::new);
    public static final Supplier<Item> BLAZE_NUCLEUS = registerWithTab("blaze_nucleus", BlazeNucleusBase::new);
    public static final Supplier<Item> ICE_CUBE = registerWithTab("ice_cube", IceCubeBase::new);
    public static final Supplier<Item> SIGIL_OF_BAPHOMET = registerWithTab("sigil_of_baphomet", SigilOfBaphometBase::new);
    public static final Supplier<Item> CREEPER_SENSE = registerWithTab("creeper_sense", CreeperSenseBase::new);
    public static final Supplier<Item> FERTILIZER = registerWithTab("fertilizer", FertilizerBase::new);
    public static final Supplier<Item> GODS_CROWN = registerWithTab("gods_crown", GodsCrownBase::new);
    public static final Supplier<Item> AMPHIBIOUS_HANDS = registerWithTab("amphibious_hands", AmphibiousHandsBase::new);
    public static final Supplier<Item> GILLS = registerWithTab("gills", GillsBase::new);
    public static final Supplier<Item> MOON_STONE = registerWithTab("moon_stone", MoonStoneBase::new);
    public static final Supplier<Item> SLEEPING_PILLS = registerWithTab("sleeping_pills", SleepingPillsBase::new);
    public static final Supplier<Item> ETHEREAL_WINGS = registerWithTab("ethereal_wings", EtherealWingsBase::new);
    public static final Supplier<Item> SPIDER_LEGS = registerWithTab("spider_legs", SpiderLegsBase::new);
    public static final Supplier<Item> REFORGER = registerWithTab("reforger", ReforgerBase::new);
    public static final Supplier<Item> ELECTRIC_PADDLE = registerWithTab("electric_paddle", ElectricPaddleBase::new);
    public static final Supplier<Item> FRACTURED_NULLSTONE = registerWithTab("fractured_nullstone", FracturedNullstoneBase::new);
    public static final Supplier<Item> POCKET_LIGHTNING_ROD = registerWithTab("pocket_lightning_rod", PocketLightningRodBase::new);
    public static final Supplier<Item> FRAGILE_CLOUD = registerWithTab("fragile_cloud", FragileCloudBase::new);
    public static final Supplier<Item> SCARAB_AMULET = registerWithTab("scarab_amulet", ScarabAmuletBase::new);
    public static final Supplier<Item> FATE_EMERALD = registerWithTab("fate_emerald", FateEmeraldBase::new);
    public static final Supplier<Item> LIGHT_GLOVES = registerWithTab("light_gloves", LightGlovesBase::new);
    public static final Supplier<Item> DRAGONS_EYE = registerWithTab("dragons_eye", DragonsEyeBase::new);
    public static final Supplier<Item> FOUR_LEAF_CLOVER = registerWithTab("four_leaf_clover", FourLeafCloverBase::new);
    public static final Supplier<Item> NELUMBO = registerWithTab("nelumbo", NelumboBase::new);
    public static final Supplier<Item> DARK_NELUMBO = registerWithTab("dark_nelumbo", DarkNelumboBase::new);
    public static final Supplier<Item> MINERS_SOUL = registerWithTab("miners_soul", MinersSoulBase::new);
    public static final Supplier<Item> TRUE_HEART_OF_THE_SEA = registerWithTab("true_heart_of_the_sea", TrueHeartOfTheSeaBase::new);
    public static final Supplier<Item> TEAR_OF_THE_SEA = registerWithTab("tear_of_the_sea", TearOfTheSeaBase::new);

    public static final Supplier<Item> SHRINKING_VEIL = registerWithTab("shrinking_veil", ShrinkingVeilBase::new);
    public static final Supplier<Item> TITANS_MARK = registerWithTab("titans_mark", TitansMarkBase::new);
    public static final Supplier<Item> WOUNDBEARER = registerWithTab("woundbearer", WoundbearerBase::new);
    public static final Supplier<Item> DYING_STAR = registerWithTab("dying_star", DyingStarBase::new);
    public static final Supplier<Item> RESONANT_HEART = registerWithTab("resonant_heart", ResonantHeartBase::new);

    public static final Supplier<Item> TRINKET_BUNDLE = registerWithTab("trinket_bundle", TrinketBundle::new);

    //Recycling
    public static final Supplier<Item> MYSTERIOUS_TRINKET = registerWithTab("mysterious_trinket", MysteriousTrinket::new);
    public static final Supplier<Item> UNKNOWN_FRAGMENT = registerWithTab("unknown_fragment", UnknownFragment::new);

    //Recipe Items
    public static final Supplier<Item> DUBIOUS_DUST = registerWithTab("dubious_dust", DubiousDust::new);
    public static final Supplier<Item> GLOWING_DUST = registerWithTab("glowing_dust", GlowingDust::new);
    public static final Supplier<Item> ULTIMATE_DUST = registerWithTab("ultimate_dust", UltimateDust::new);

    public static Supplier<Item> registerWithTab(final String name, final Supplier<? extends Item> supplier) {
        Supplier<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
