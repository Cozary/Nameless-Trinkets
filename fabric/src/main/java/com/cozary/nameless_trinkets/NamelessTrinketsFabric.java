package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.config.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.events.*;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.loottables.LootTableHandler;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import com.cozary.nameless_trinkets.utils.RemoveRendering;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NamelessTrinketsFabric implements ModInitializer {

    private static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "nameless_trinkets"));

    @Override
    public void onInitialize() {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.nameless_trinkets"))
                .icon(() -> new ItemStack(ModItems.MYSTERIOUS_TRINKET.get()))
                .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
                .build()
        );


        NamelessTrinkets.init();

        NeoForgeConfigRegistry.INSTANCE.register(NamelessTrinkets.MOD_ID, ModConfig.Type.COMMON, ConfigurationHandler.spec);

        AmphibiousHandsEvents.register();
        BlazeNucleusEvents.register();
        BrokenAnkhEvents.register();
        CallusEvents.register();
        ExperienceBatteryEvents.register();
        ExplosionProofJacketEvents.register();
        FourLeafCloverEvents.register();
        FracturedNullstoneEvents.register();
        GhastEyeEvents.register();
        IceCubeEvents.register();
        LightGlovesEvents.register();
        LuckyRockEvents.register();
        MinersSoulEvents.register();
        MissingPageEvents.register();
        MoonStoneEvents.register();
        PufferFishLiverEvents.register();
        RageMindEvents.register();
        ReverseCardEvents.register();
        ScarabAmuletEvents.register();
        SigilOfBaphometEvents.register();
        SleepingPillsEvents.register();
        TrueHeartOfTheSeaEvents.register();
        VampireBloodEvents.register();
        WoodenStickEvents.register();
        WoundbearerEvents.register();
        UnknownFragmentEvent.register();

        LootTableHandler.modifyLootTable();

        TrinketConfigs.loadClass();
        TrinketLootConfigsManager.loadConfigs();
        RemoveRendering.noRenderingList();

    }
}
