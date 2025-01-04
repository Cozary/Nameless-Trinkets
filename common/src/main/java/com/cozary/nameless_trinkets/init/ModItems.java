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
    public static final RegistryObject<Item> MISSING_PAGE = registerWithTab("missing_page", MissingPage::new);
    public static final RegistryObject<Item> REVERSE_CARD = registerWithTab("reverse_card", ReverseCard::new);
    public static final RegistryObject<Item> EXPERIENCE_BATTERY = registerWithTab("experience_battery", ExperienceBattery::new);
    public static final RegistryObject<Item> BROKEN_ANKH = registerWithTab("broken_ankh", BrokenAnkh::new);
    public static final RegistryObject<Item> EXPERIENCE_MAGNET = registerWithTab("experience_magnet", ExperienceMagnet::new);
    public static final RegistryObject<Item> BROKEN_MAGNET = registerWithTab("broken_magnet", BrokenMagnet::new);
    public static final RegistryObject<Item> SUPER_MAGNET = registerWithTab("super_magnet", SuperMagnet::new);
    public static final RegistryObject<Item> WHAT_MAGNET = registerWithTab("what_magnet", WhatMagnet::new);
    public static final RegistryObject<Item> CALLUS = registerWithTab("callus", Callus::new);
    public static final RegistryObject<Item> SPEED_FORCE = registerWithTab("speed_force", SpeedForce::new);
    public static final RegistryObject<Item> VAMPIRE_BLOOD = registerWithTab("vampire_blood", VampireBlood::new);
    public static final RegistryObject<Item> LUCKY_ROCK = registerWithTab("lucky_rock", LuckyRock::new);
    public static final RegistryObject<Item> PUFFER_FISH_LIVER = registerWithTab("puffer_fish_liver", PufferFishLiver::new);
    public static final RegistryObject<Item> RAGE_MIND = registerWithTab("rage_mind", RageMind::new);
    public static final RegistryObject<Item> TICK = registerWithTab("tick", Tick::new);
    public static final RegistryObject<Item> BLINDFOLD = registerWithTab("blindfold", Blindfold::new);
    public static final RegistryObject<Item> EXPLOSION_PROOF_JACKET = registerWithTab("explosion_proof_jacket", ExplosionProofJacket::new);
    public static final RegistryObject<Item> CRACKED_CROWN = registerWithTab("cracked_crown", CrackedCrown::new);
    public static final RegistryObject<Item> GHAST_EYE = registerWithTab("ghast_eye", GhastEye::new);
    public static final RegistryObject<Item> WOODEN_STICK = registerWithTab("wooden_stick", WoodenStick::new);
    public static final RegistryObject<Item> BLAZE_NUCLEUS = registerWithTab("blaze_nucleus", BlazeNucleus::new);
    public static final RegistryObject<Item> ICE_CUBE = registerWithTab("ice_cube", IceCube::new);
    public static final RegistryObject<Item> SIGIL_OF_BAPHOMET = registerWithTab("sigil_of_baphomet", SigilOfBaphomet::new);
    public static final RegistryObject<Item> CREEPER_SENSE = registerWithTab("creeper_sense", CreeperSense::new);
    public static final RegistryObject<Item> FERTILIZER = registerWithTab("fertilizer", Fertilizer::new);
    public static final RegistryObject<Item> GODS_CROWN = registerWithTab("gods_crown", GodsCrown::new);
    public static final RegistryObject<Item> AMPHIBIOUS_HANDS = registerWithTab("amphibious_hands", AmphibiousHands::new);
    public static final RegistryObject<Item> GILLS = registerWithTab("gills", Gills::new);
    public static final RegistryObject<Item> MOON_STONE = registerWithTab("moon_stone", MoonStone::new);
    public static final RegistryObject<Item> SLEEPING_PILLS = registerWithTab("sleeping_pills", SleepingPills::new);
    public static final RegistryObject<Item> ETHEREAL_WINGS = registerWithTab("ethereal_wings", EtherealWings::new);
    public static final RegistryObject<Item> SPIDER_LEGS = registerWithTab("spider_legs", SpiderLegs::new);
    public static final RegistryObject<Item> REFORGER = registerWithTab("reforger", Reforger::new);
    public static final RegistryObject<Item> ELECTRIC_PADDLE = registerWithTab("electric_paddle", ElectricPaddle::new);
    public static final RegistryObject<Item> FRACTURED_NULLSTONE = registerWithTab("fractured_nullstone", FracturedNullstone::new);
    public static final RegistryObject<Item> POCKET_LIGHTNING_ROD = registerWithTab("pocket_lightning_rod", PocketLightningRod::new);
    public static final RegistryObject<Item> FRAGILE_CLOUD = registerWithTab("fragile_cloud", FragileCloud::new);
    public static final RegistryObject<Item> SCARAB_AMULET = registerWithTab("scarab_amulet", ScarabAmulet::new);
    public static final RegistryObject<Item> FATE_EMERALD = registerWithTab("fate_emerald", FateEmerald::new);
    public static final RegistryObject<Item> LIGHT_GLOVES = registerWithTab("light_gloves", LightGloves::new);
    public static final RegistryObject<Item> DRAGONS_EYE = registerWithTab("dragons_eye", DragonsEye::new);
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER = registerWithTab("four_leaf_clover", FourLeafClover::new);
    public static final RegistryObject<Item> NELUMBO = registerWithTab("nelumbo", Nelumbo::new);
    public static final RegistryObject<Item> DARK_NELUMBO = registerWithTab("dark_nelumbo", DarkNelumbo::new);
    public static final RegistryObject<Item> MINERS_SOUL = registerWithTab("miners_soul", MinersSoul::new);
    public static final RegistryObject<Item> TRUE_HEART_OF_THE_SEA = registerWithTab("true_heart_of_the_sea", TrueHeartOfTheSea::new);
    public static final RegistryObject<Item> TEAR_OF_THE_SEA = registerWithTab("tear_of_the_sea", TearOfTheSea::new);

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
