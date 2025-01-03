package com.cozary.nameless_trinkets;


import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.init.ModCodec;
import com.cozary.nameless_trinkets.init.ModTabs;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(NamelessTrinkets.MOD_ID)
public class NamelessTrinketsNeoForge {

    public NamelessTrinketsNeoForge(IEventBus eventBus, ModContainer container) {

        eventBus.addListener(EventPriority.LOWEST, this::setup);

        NamelessTrinkets.init();

        ModTabs.init(eventBus);
        ModCodec.init(eventBus);

        container.registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
    }

    private void setup(final FMLCommonSetupEvent event) {
        TrinketConfigs.loadClass();
    }

}