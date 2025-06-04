package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTabs;
import com.cozary.nameless_trinkets.items.trinkets.SpeedForce;
import com.cozary.nameless_trinkets.util.RemoveRendering;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(NamelessTrinkets.MOD_ID)
public class NamelessTrinketsNeoForge {

    public NamelessTrinketsNeoForge(IEventBus eventBus, ModContainer container) {


        NamelessTrinkets.init();

        ModTabs.init(eventBus);

        eventBus.addListener(EventPriority.LOWEST, this::setup);

    }

    private void setup(final FMLCommonSetupEvent event) {
        TrinketLootConfigsManager.loadConfigs();
        TrinketConfigs.loadClass();
        CommonConfigManager.loadConfig();
        RemoveRendering.noRenderingList();
    }
}