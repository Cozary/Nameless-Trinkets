package com.cozary.nameless_trinkets.platform;

import net.minecraft.world.item.Item;

import java.util.ServiceLoader;

public class PlatformAbstractions {

    private static final IPlatformAbstractions IMPL = ServiceLoader
            .load(IPlatformAbstractions.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No platform implementation found for IPlatformAbstractions"));

    static {
        System.out.println("Loaded PlatformAbstractions: " + IMPL.getClass().getName());
    }

    public static Item createMissingPageItem() {
        return IMPL.createMissingPageItem();
    }

    public static Item createReverseCardItem()  {
        return IMPL.createReverseCardItem();
    }

    public static Item createExperienceBatteryItem() {
        return IMPL.createExperienceBatteryItem();
    }

    public static Item createBrokenAnkhItem() {
        return IMPL.createBrokenAnkhItem();
    }

    public static Item createExperienceMagnetItem() {
        return IMPL.createExperienceMagnetItem();
    }

    public static Item createBrokenMagnetItem() {
        return IMPL.createBrokenMagnetItem();
    }

    public static Item createSuperMagnetItem() {
        return IMPL.createSuperMagnetItem();
    }

    public static Item createWhatMagnetItem() {
        return IMPL.createWhatMagnetItem();
    }

    public static Item createCallusItem() {
        return IMPL.createCallusItem();
    }

    public static Item createSpeedForceItem() {
        return IMPL.createSpeedForceItem();
    }

    public static Item createVampireBloodItem() {
        return IMPL.createVampireBloodItem();
    }

    public static Item createLuckyRockItem() {
        return IMPL.createLuckyRockItem();
    }

    public static Item createPufferFishLiverItem() {
        return IMPL.createPufferFishLiverItem();
    }

    public static Item createRageMindItem() {
        return IMPL.createRageMindItem();
    }

    public static Item createTickItem() {
        return IMPL.createTickItem();
    }

    public static Item createBlindfoldItem() {
        return IMPL.createBlindfoldItem();
    }

    public static Item createExplosionProofJacketItem() {
        return IMPL.createExplosionProofJacketItem();
    }

    public static Item createCrackedCrownItem() {
        return IMPL.createCrackedCrownItem();
    }

    public static Item createGhastEyeItem() {
        return IMPL.createGhastEyeItem();
    }

    public static Item createWoodenStickItem() {
        return IMPL.createWoodenStickItem();
    }

    public static Item createBlazeNucleusItem() {
        return IMPL.createBlazeNucleusItem();
    }

    public static Item createIceCubeItem() {
        return IMPL.createIceCubeItem();
    }

    public static Item createSigilOfBaphometItem() {
        return IMPL.createSigilOfBaphometItem();
    }

    public static Item createCreeperSenseItem() {
        return IMPL.createCreeperSenseItem();
    }

    public static Item createFertilizerItem() {
        return IMPL.createFertilizerItem();
    }

    public static Item createGodsCrownItem() {
        return IMPL.createGodsCrownItem();
    }

    public static Item createAmphibiousHandsItem() {
        return IMPL.createAmphibiousHandsItem();
    }

    public static Item createGillsItem() {
        return IMPL.createGillsItem();
    }

    public static Item createMoonStoneItem() {
        return IMPL.createMoonStoneItem();
    }

    public static Item createSleepingPillsItem() {
        return IMPL.createSleepingPillsItem();
    }

    public static Item createEtherealWingsItem() {
        return IMPL.createEtherealWingsItem();
    }

    public static Item createSpiderLegsItem() {
        return IMPL.createSpiderLegsItem();
    }

    public static Item createReforgerItem() {
        return IMPL.createReforgerItem();
    }

    public static Item createElectricPaddleItem() {
        return IMPL.createElectricPaddleItem();
    }

    public static Item createFracturedNullstoneItem() {
        return IMPL.createFracturedNullstoneItem();
    }

    public static Item createPocketLightningRodItem() {
        return IMPL.createPocketLightningRodItem();
    }

    public static Item createFragileCloudItem() {
        return IMPL.createFragileCloudItem();
    }

    public static Item createScarabAmuletItem() {
        return IMPL.createScarabAmuletItem();
    }

    public static Item createFateEmeraldItem() {
        return IMPL.createFateEmeraldItem();
    }

    public static Item createLightGlovesItem() {
        return IMPL.createLightGlovesItem();
    }

    public static Item createDragonsEyeItem() {
        return IMPL.createDragonsEyeItem();
    }

    public static Item createFourLeafCloverItem() {
        return IMPL.createFourLeafCloverItem();
    }

    public static Item createNelumboItem() {
        return IMPL.createNelumboItem();
    }

    public static Item createDarkNelumboItem() {
        return IMPL.createDarkNelumboItem();
    }

    public static Item createMinersSoulItem() {
        return IMPL.createMinersSoulItem();
    }

    public static Item createTrueHeartOfTheSeaItem() {
        return IMPL.createTrueHeartOfTheSeaItem();
    }

    public static Item createTearOfTheSeaItem() {
        return IMPL.createTearOfTheSeaItem();
    }

    public static Item createShrinkingVeilItem() {
        return IMPL.createShrinkingVeilItem();
    }

    public static Item createTitansMarkItem() {
        return IMPL.createTitansMarkItem();
    }

    public static Item createWoundbearerItem() {
        return IMPL.createWoundbearerItem();
    }

    public static Item createDyingStarItem() {
        return IMPL.createDyingStarItem();
    }

    public static Item createResonantHeartItem() {
        return IMPL.createResonantHeartItem();
    }
}