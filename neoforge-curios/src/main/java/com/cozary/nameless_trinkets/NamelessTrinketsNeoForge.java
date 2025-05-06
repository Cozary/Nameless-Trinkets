package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import com.cozary.nameless_trinkets.init.ModItemsCurios;
import com.cozary.nameless_trinkets.init.ModTabs;
import com.cozary.nameless_trinkets.platform.Services;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(NamelessTrinkets.MOD_ID)
public class NamelessTrinketsNeoForge {

    public NamelessTrinketsNeoForge(IEventBus eventBus, ModContainer container) {

        eventBus.addListener(EventPriority.LOWEST, this::setup);

        NamelessTrinkets.init();

        ModTabs.init(eventBus);
        //ModItemsCurios.init(eventBus);

        if (Services.PLATFORM.isModLoaded("curios")) {
            try {
                Class<?> clazz = Class.forName("com.cozary.nameless_trinkets.util.ModCuriosEntryPoint");
                clazz.getMethod("tryLoadCuriosItems").invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setup(final FMLCommonSetupEvent event) {
        //TrinketLootConfigsManager.loadConfigs();
        //TrinketConfigs.loadClass();
        CommonConfigManager.loadConfig();
        //RemoveRendering.noRenderingList();
    }
}