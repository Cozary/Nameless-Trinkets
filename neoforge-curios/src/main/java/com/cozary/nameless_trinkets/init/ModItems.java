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

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, NamelessTrinkets.MOD_ID);

    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    //Trinkets
    public static final Supplier<Item> MISSING_PAGE = registerWithTab("missing_page", MissingPage::new);
    public static final Supplier<Item> REVERSE_CARD = registerWithTab("reverse_card", ReverseCard::new);
    public static final Supplier<Item> EXPERIENCE_BATTERY = registerWithTab("experience_battery", ExperienceBattery::new);
    public static final Supplier<Item> BROKEN_ANKH = registerWithTab("broken_ankh", BrokenAnkh::new);
    public static final Supplier<Item> EXPERIENCE_MAGNET = registerWithTab("experience_magnet", ExperienceMagnet::new);
    public static final Supplier<Item> BROKEN_MAGNET = registerWithTab("broken_magnet", BrokenMagnet::new);
    public static final Supplier<Item> SUPER_MAGNET = registerWithTab("super_magnet", SuperMagnet::new);
    public static final Supplier<Item> WHAT_MAGNET = registerWithTab("what_magnet", WhatMagnet::new);
    public static final Supplier<Item> CALLUS = registerWithTab("callus", Callus::new);
    public static final Supplier<Item> SPEED_FORCE = registerWithTab("speed_force", SpeedForce::new);
    public static final Supplier<Item> VAMPIRE_BLOOD = registerWithTab("vampire_blood", VampireBlood::new);
    public static final Supplier<Item> LUCKY_ROCK = registerWithTab("lucky_rock", LuckyRock::new);
    public static final Supplier<Item> PUFFER_FISH_LIVER = registerWithTab("puffer_fish_liver", PufferFishLiver::new);
    public static final Supplier<Item> RAGE_MIND = registerWithTab("rage_mind", RageMind::new);
    public static final Supplier<Item> TICK = registerWithTab("tick", Tick::new);
    public static final Supplier<Item> BLINDFOLD = registerWithTab("blindfold", Blindfold::new);
    public static final Supplier<Item> EXPLOSION_PROOF_JACKET = registerWithTab("explosion_proof_jacket", ExplosionProofJacket::new);
    public static final Supplier<Item> CRACKED_CROWN = registerWithTab("cracked_crown", CrackedCrown::new);
    public static final Supplier<Item> GHAST_EYE = registerWithTab("ghast_eye", GhastEye::new);
    public static final Supplier<Item> WOODEN_STICK = registerWithTab("wooden_stick", WoodenStick::new);
    public static final Supplier<Item> BLAZE_NUCLEUS = registerWithTab("blaze_nucleus", BlazeNucleus::new);
    public static final Supplier<Item> ICE_CUBE = registerWithTab("ice_cube", IceCube::new);
    public static final Supplier<Item> SIGIL_OF_BAPHOMET = registerWithTab("sigil_of_baphomet", SigilOfBaphomet::new);
    public static final Supplier<Item> CREEPER_SENSE = registerWithTab("creeper_sense", CreeperSense::new);
    public static final Supplier<Item> FERTILIZER = registerWithTab("fertilizer", Fertilizer::new);
    public static final Supplier<Item> GODS_CROWN = registerWithTab("gods_crown", GodsCrown::new);
    public static final Supplier<Item> AMPHIBIOUS_HANDS = registerWithTab("amphibious_hands", AmphibiousHands::new);
    public static final Supplier<Item> GILLS = registerWithTab("gills", Gills::new);
    public static final Supplier<Item> MOON_STONE = registerWithTab("moon_stone", MoonStone::new);
    public static final Supplier<Item> SLEEPING_PILLS = registerWithTab("sleeping_pills", SleepingPills::new);
    public static final Supplier<Item> ETHEREAL_WINGS = registerWithTab("ethereal_wings", EtherealWings::new);
    public static final Supplier<Item> SPIDER_LEGS = registerWithTab("spider_legs", SpiderLegs::new);
    public static final Supplier<Item> REFORGER = registerWithTab("reforger", Reforger::new);
    public static final Supplier<Item> ELECTRIC_PADDLE = registerWithTab("electric_paddle", ElectricPaddle::new);
    public static final Supplier<Item> FRACTURED_NULLSTONE = registerWithTab("fractured_nullstone", FracturedNullstone::new);
    public static final Supplier<Item> POCKET_LIGHTNING_ROD = registerWithTab("pocket_lightning_rod", PocketLightningRod::new);
    public static final Supplier<Item> FRAGILE_CLOUD = registerWithTab("fragile_cloud", FragileCloud::new);
    public static final Supplier<Item> SCARAB_AMULET = registerWithTab("scarab_amulet", ScarabAmulet::new);
    public static final Supplier<Item> FATE_EMERALD = registerWithTab("fate_emerald", FateEmerald::new);
    public static final Supplier<Item> LIGHT_GLOVES = registerWithTab("light_gloves", LightGloves::new);
    public static final Supplier<Item> DRAGONS_EYE = registerWithTab("dragons_eye", DragonsEye::new);
    public static final Supplier<Item> FOUR_LEAF_CLOVER = registerWithTab("four_leaf_clover", FourLeafClover::new);
    public static final Supplier<Item> NELUMBO = registerWithTab("nelumbo", Nelumbo::new);
    public static final Supplier<Item> DARK_NELUMBO = registerWithTab("dark_nelumbo", DarkNelumbo::new);
    public static final Supplier<Item> MINERS_SOUL = registerWithTab("miners_soul", MinersSoul::new);
    public static final Supplier<Item> TRUE_HEART_OF_THE_SEA = registerWithTab("true_heart_of_the_sea", TrueHeartOfTheSea::new);
    public static final Supplier<Item> TEAR_OF_THE_SEA = registerWithTab("tear_of_the_sea", TearOfTheSea::new);

    public static final Supplier<Item> SHRINKING_VEIL = registerWithTab("shrinking_veil", ShrinkingVeil::new);
    public static final Supplier<Item> TITANS_MARK = registerWithTab("titans_mark", TitansMark::new);
    public static final Supplier<Item> WOUNDBEARER = registerWithTab("woundbearer", Woundbearer::new);
    public static final Supplier<Item> DYING_STAR = registerWithTab("dying_star", DyingStar::new);
    public static final Supplier<Item> RESONANT_HEART = registerWithTab("resonant_heart", ResonantHeart::new);

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
