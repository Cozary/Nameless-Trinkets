package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.events.*;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.lootTables.LootTableHandler;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import com.cozary.nameless_trinkets.utils.RemoveRendering;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.config.ModConfig;
import com.cozary.nameless_trinkets.recipe.RecipeGate;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class NamelessTrinketsFabric implements ModInitializer {

    private static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "nameless_trinkets"));

    @Override
    public void onInitialize() {
        NamelessTrinkets.init();

        NeoForgeConfigRegistry.INSTANCE.register(NamelessTrinkets.MOD_ID, ModConfig.Type.COMMON, ConfigurationHandler.spec);

        // Applies recipe gating on server start + after /reload
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            RecipeGate.apply(server);
        });
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) RecipeGate.apply(server);
        });

        eventLoad();
        itemGroupLoad();

        LootTableHandler.modifyLootTable();

        TrinketConfigs.loadClass();
        TrinketLootConfigsManager.loadConfigs();
        RemoveRendering.noRenderingList();

    }

    private void eventLoad() {
        AmphibiousHandsEvents.register();
        BlazeNucleusEvents.register();
        BrokenAnkhEvents.register();
        CallusEvents.register();
        DarkNelumboEvents.register();
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
        ResonantHeartEvents.register();
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
    }

    private void itemGroupLoad() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.nameless_trinkets"))
                .icon(() -> new ItemStack(ModItems.MYSTERIOUS_TRINKET.get()))
                .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
                .build()
        );
    }

}
