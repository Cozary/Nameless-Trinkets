package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.init.ModTabs;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(NamelessTrinkets.MOD_ID)
public class NamelessTrinketsNeoForgeCurios {

    public NamelessTrinketsNeoForgeCurios(IEventBus eventBus, ModContainer container) {

        eventBus.addListener(EventPriority.LOWEST, this::setup);

        NamelessTrinkets.init();

        ModTabs.init(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        TrinketLootConfigsManager.loadConfigs();
        TrinketConfigs.loadClass();
        CommonConfigManager.loadConfig();
    }
}