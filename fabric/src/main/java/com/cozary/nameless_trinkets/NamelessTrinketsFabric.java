package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.events.*;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.lootTables.LootTableHandler;
import com.cozary.nameless_trinkets.recipe.RecipeGate;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;


public class NamelessTrinketsFabric implements ModInitializer {

    private static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "nameless_trinkets"));

    @Override
    public void onInitialize() {
        NamelessTrinkets.init();

        eventLoad();
        itemGroupLoad();

        for (var itemObj : ModItems.CREATIVE_TAB_ITEMS) {
            net.minecraft.world.item.Item item = itemObj.get();
            if (item instanceof Trinket trinket) {
                TrinketsApi.registerTrinket(item, trinket);
            }
        }

        LootTableHandler.modifyLootTable();

        TrinketConfigs.loadClass();
        TrinketLootConfigsManager.loadConfigs();
        CommonConfigManager.loadConfig();


        // Applies recipe gating on server start + after /reload
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            RecipeGate.apply(server);
        });
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) {
                CommonConfigManager.loadConfig();
                RecipeGate.apply(server);
            }
        });
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