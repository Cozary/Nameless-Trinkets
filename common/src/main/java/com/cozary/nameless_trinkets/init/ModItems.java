package com.cozary.nameless_trinkets.init;


import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.special.*;
import com.cozary.nameless_trinkets.platform.PlatformAbstractions;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {

    private static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, NamelessTrinkets.MOD_ID);

    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    //Trinkets
    public static final RegistryObject<Item> MISSING_PAGE = registerWithTab("missing_page", PlatformAbstractions::createMissingPageItem);
    public static final RegistryObject<Item> REVERSE_CARD = registerWithTab("reverse_card", PlatformAbstractions::createReverseCardItem);
    public static final RegistryObject<Item> EXPERIENCE_BATTERY = registerWithTab("experience_battery", PlatformAbstractions::createExperienceBatteryItem);
    public static final RegistryObject<Item> BROKEN_ANKH = registerWithTab("broken_ankh", PlatformAbstractions::createBrokenAnkhItem);
    public static final RegistryObject<Item> EXPERIENCE_MAGNET = registerWithTab("experience_magnet", PlatformAbstractions::createExperienceMagnetItem);
    public static final RegistryObject<Item> BROKEN_MAGNET = registerWithTab("broken_magnet", PlatformAbstractions::createBrokenMagnetItem);
    public static final RegistryObject<Item> SUPER_MAGNET = registerWithTab("super_magnet", PlatformAbstractions::createSuperMagnetItem);
    public static final RegistryObject<Item> WHAT_MAGNET = registerWithTab("what_magnet", PlatformAbstractions::createWhatMagnetItem);
    public static final RegistryObject<Item> CALLUS = registerWithTab("callus", PlatformAbstractions::createCallusItem);
    public static final RegistryObject<Item> SPEED_FORCE = registerWithTab("speed_force", PlatformAbstractions::createSpeedForceItem);
    public static final RegistryObject<Item> VAMPIRE_BLOOD = registerWithTab("vampire_blood", PlatformAbstractions::createVampireBloodItem);
    public static final RegistryObject<Item> LUCKY_ROCK = registerWithTab("lucky_rock", PlatformAbstractions::createLuckyRockItem);
    public static final RegistryObject<Item> PUFFER_FISH_LIVER = registerWithTab("puffer_fish_liver", PlatformAbstractions::createPufferFishLiverItem);
    public static final RegistryObject<Item> RAGE_MIND = registerWithTab("rage_mind", PlatformAbstractions::createRageMindItem);
    public static final RegistryObject<Item> TICK = registerWithTab("tick", PlatformAbstractions::createTickItem);
    public static final RegistryObject<Item> BLINDFOLD = registerWithTab("blindfold", PlatformAbstractions::createBlindfoldItem);
    public static final RegistryObject<Item> EXPLOSION_PROOF_JACKET = registerWithTab("explosion_proof_jacket", PlatformAbstractions::createExplosionProofJacketItem);
    public static final RegistryObject<Item> CRACKED_CROWN = registerWithTab("cracked_crown", PlatformAbstractions::createCrackedCrownItem);
    public static final RegistryObject<Item> GHAST_EYE = registerWithTab("ghast_eye", PlatformAbstractions::createGhastEyeItem);
    public static final RegistryObject<Item> WOODEN_STICK = registerWithTab("wooden_stick", PlatformAbstractions::createWoodenStickItem);
    public static final RegistryObject<Item> BLAZE_NUCLEUS = registerWithTab("blaze_nucleus", PlatformAbstractions::createBlazeNucleusItem);
    public static final RegistryObject<Item> ICE_CUBE = registerWithTab("ice_cube", PlatformAbstractions::createIceCubeItem);
    public static final RegistryObject<Item> SIGIL_OF_BAPHOMET = registerWithTab("sigil_of_baphomet", PlatformAbstractions::createSigilOfBaphometItem);
    public static final RegistryObject<Item> CREEPER_SENSE = registerWithTab("creeper_sense", PlatformAbstractions::createCreeperSenseItem);
    public static final RegistryObject<Item> FERTILIZER = registerWithTab("fertilizer", PlatformAbstractions::createFertilizerItem);
    public static final RegistryObject<Item> GODS_CROWN = registerWithTab("gods_crown", PlatformAbstractions::createGodsCrownItem);
    public static final RegistryObject<Item> AMPHIBIOUS_HANDS = registerWithTab("amphibious_hands", PlatformAbstractions::createAmphibiousHandsItem);
    public static final RegistryObject<Item> GILLS = registerWithTab("gills", PlatformAbstractions::createGillsItem);
    public static final RegistryObject<Item> MOON_STONE = registerWithTab("moon_stone", PlatformAbstractions::createMoonStoneItem);
    public static final RegistryObject<Item> SLEEPING_PILLS = registerWithTab("sleeping_pills", PlatformAbstractions::createSleepingPillsItem);
    public static final RegistryObject<Item> ETHEREAL_WINGS = registerWithTab("ethereal_wings", PlatformAbstractions::createEtherealWingsItem);
    public static final RegistryObject<Item> SPIDER_LEGS = registerWithTab("spider_legs", PlatformAbstractions::createSpiderLegsItem);
    public static final RegistryObject<Item> REFORGER = registerWithTab("reforger", PlatformAbstractions::createReforgerItem);
    public static final RegistryObject<Item> ELECTRIC_PADDLE = registerWithTab("electric_paddle", PlatformAbstractions::createElectricPaddleItem);
    public static final RegistryObject<Item> FRACTURED_NULLSTONE = registerWithTab("fractured_nullstone", PlatformAbstractions::createFracturedNullstoneItem);
    public static final RegistryObject<Item> POCKET_LIGHTNING_ROD = registerWithTab("pocket_lightning_rod", PlatformAbstractions::createPocketLightningRodItem);
    public static final RegistryObject<Item> FRAGILE_CLOUD = registerWithTab("fragile_cloud", PlatformAbstractions::createFragileCloudItem);
    public static final RegistryObject<Item> SCARAB_AMULET = registerWithTab("scarab_amulet", PlatformAbstractions::createScarabAmuletItem);
    public static final RegistryObject<Item> FATE_EMERALD = registerWithTab("fate_emerald", PlatformAbstractions::createFateEmeraldItem);
    public static final RegistryObject<Item> LIGHT_GLOVES = registerWithTab("light_gloves", PlatformAbstractions::createLightGlovesItem);
    public static final RegistryObject<Item> DRAGONS_EYE = registerWithTab("dragons_eye", PlatformAbstractions::createDragonsEyeItem);
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER = registerWithTab("four_leaf_clover", PlatformAbstractions::createFourLeafCloverItem);
    public static final RegistryObject<Item> NELUMBO = registerWithTab("nelumbo", PlatformAbstractions::createNelumboItem);
    public static final RegistryObject<Item> DARK_NELUMBO = registerWithTab("dark_nelumbo", PlatformAbstractions::createDarkNelumboItem);
    public static final RegistryObject<Item> MINERS_SOUL = registerWithTab("miners_soul", PlatformAbstractions::createMinersSoulItem);
    public static final RegistryObject<Item> TRUE_HEART_OF_THE_SEA = registerWithTab("true_heart_of_the_sea", PlatformAbstractions::createTrueHeartOfTheSeaItem);
    public static final RegistryObject<Item> TEAR_OF_THE_SEA = registerWithTab("tear_of_the_sea", PlatformAbstractions::createTearOfTheSeaItem);

    public static final RegistryObject<Item> SHRINKING_VEIL = registerWithTab("shrinking_veil", PlatformAbstractions::createShrinkingVeilItem);
    public static final RegistryObject<Item> TITANS_MARK = registerWithTab("titans_mark", PlatformAbstractions::createTitansMarkItem);
    public static final RegistryObject<Item> WOUNDBEARER = registerWithTab("woundbearer", PlatformAbstractions::createWoundbearerItem);
    public static final RegistryObject<Item> DYING_STAR = registerWithTab("dying_star", PlatformAbstractions::createDyingStarItem);
    public static final RegistryObject<Item> RESONANT_HEART = registerWithTab("resonant_heart", PlatformAbstractions::createResonantHeartItem);
    public static final RegistryObject<Item> LUNAR_CREST = registerWithTab("lunar_crest", PlatformAbstractions::createLunarCrestItem);
    public static final RegistryObject<Item> ECLIPSE_ASHES = registerWithTab("eclipse_ashes", PlatformAbstractions::createEclipseAshesItem);

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
