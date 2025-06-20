package com.cozary.nameless_trinkets.platform;

import com.cozary.nameless_trinkets.items.trinkets.*;
import net.minecraft.world.item.Item;

public class PlatformAbstractionsImpl implements IPlatformAbstractions {

    public PlatformAbstractionsImpl() {
    }

    @Override
    public Item createMissingPageItem() {
        return new MissingPage();
    }

    @Override
    public Item createReverseCardItem() {
        return new ReverseCard();
    }

    @Override
    public Item createExperienceBatteryItem() {
        return new ExperienceBattery();
    }

    @Override
    public Item createBrokenAnkhItem() {
        return new BrokenAnkh();
    }

    @Override
    public Item createExperienceMagnetItem() {
        return new ExperienceMagnet();
    }

    @Override
    public Item createBrokenMagnetItem() {
        return new BrokenMagnet();
    }

    @Override
    public Item createSuperMagnetItem() {
        return new SuperMagnet();
    }

    @Override
    public Item createWhatMagnetItem() {
        return new WhatMagnet();
    }

    @Override
    public Item createCallusItem() {
        return new Callus();
    }

    @Override
    public Item createSpeedForceItem() {
        return new SpeedForce();
    }

    @Override
    public Item createVampireBloodItem() {
        return new VampireBlood();
    }

    @Override
    public Item createLuckyRockItem() {
        return new LuckyRock();
    }

    @Override
    public Item createPufferFishLiverItem() {
        return new PufferFishLiver();
    }

    @Override
    public Item createRageMindItem() {
        return new RageMind();
    }

    @Override
    public Item createTickItem() {
        return new Tick();
    }

    @Override
    public Item createBlindfoldItem() {
        return new Blindfold();
    }

    @Override
    public Item createExplosionProofJacketItem() {
        return new ExplosionProofJacket();
    }

    @Override
    public Item createCrackedCrownItem() {
        return new CrackedCrown();
    }

    @Override
    public Item createGhastEyeItem() {
        return new GhastEye();
    }

    @Override
    public Item createWoodenStickItem() {
        return new WoodenStick();
    }

    @Override
    public Item createBlazeNucleusItem() {
        return new BlazeNucleus();
    }

    @Override
    public Item createIceCubeItem() {
        return new IceCube();
    }

    @Override
    public Item createSigilOfBaphometItem() {
        return new SigilOfBaphomet();
    }

    @Override
    public Item createCreeperSenseItem() {
        return new CreeperSense();
    }

    @Override
    public Item createFertilizerItem() {
        return new Fertilizer();
    }

    @Override
    public Item createGodsCrownItem() {
        return new GodsCrown();
    }

    @Override
    public Item createAmphibiousHandsItem() {
        return new AmphibiousHands();
    }

    @Override
    public Item createGillsItem() {
        return new Gills();
    }

    @Override
    public Item createMoonStoneItem() {
        return new MoonStone();
    }

    @Override
    public Item createSleepingPillsItem() {
        return new SleepingPills();
    }

    @Override
    public Item createEtherealWingsItem() {
        return new EtherealWings();
    }

    @Override
    public Item createSpiderLegsItem() {
        return new SpiderLegs();
    }

    @Override
    public Item createReforgerItem() {
        return new Reforger();
    }

    @Override
    public Item createElectricPaddleItem() {
        return new ElectricPaddle();
    }

    @Override
    public Item createFracturedNullstoneItem() {
        return new FracturedNullstone();
    }

    @Override
    public Item createPocketLightningRodItem() {
        return new PocketLightningRod();
    }

    @Override
    public Item createFragileCloudItem() {
        return new FragileCloud();
    }

    @Override
    public Item createScarabAmuletItem() {
        return new ScarabAmulet();
    }

    @Override
    public Item createFateEmeraldItem() {
        return new FateEmerald();
    }

    @Override
    public Item createLightGlovesItem() {
        return new LightGloves();
    }

    @Override
    public Item createDragonsEyeItem() {
        return new DragonsEye();
    }

    @Override
    public Item createFourLeafCloverItem() {
        return new FourLeafClover();
    }

    @Override
    public Item createNelumboItem() {
        return new Nelumbo();
    }

    @Override
    public Item createDarkNelumboItem() {
        return new DarkNelumbo();
    }

    @Override
    public Item createMinersSoulItem() {
        return new MinersSoul();
    }

    @Override
    public Item createTrueHeartOfTheSeaItem() {
        return new TrueHeartOfTheSea();
    }

    @Override
    public Item createTearOfTheSeaItem() {
        return new TearOfTheSea();
    }

    @Override
    public Item createShrinkingVeilItem() {
        return new ShrinkingVeil();
    }

    @Override
    public Item createWoundbearerItem() {
        return new Woundbearer();
    }

    @Override
    public Item createTitansMarkItem() {
        return new TitansMark();
    }

    @Override
    public Item createDyingStarItem() {
        return new DyingStar();
    }

    @Override
    public Item createResonantHeartItem() {
        return new ResonantHeart();
    }
}