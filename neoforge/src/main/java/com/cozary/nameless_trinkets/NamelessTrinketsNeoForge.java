package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.config.looTables.TrinketLootConfigsManager;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTabs;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import com.cozary.nameless_trinkets.utils.RemoveRendering;
import com.cozary.nameless_trinkets.utils.TrinketBundleContents;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Objects;

@Mod(NamelessTrinkets.MOD_ID)
public class NamelessTrinketsNeoForge {

    public NamelessTrinketsNeoForge(IEventBus eventBus, ModContainer container) {

        eventBus.addListener(EventPriority.LOWEST, this::setup);

        NamelessTrinkets.init();

        ModTabs.init(eventBus);
        //ModCodec.init(eventBus);

        container.registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);

    }

    private void setup(final FMLCommonSetupEvent event) {
        TrinketLootConfigsManager.loadConfigs();
        TrinketConfigs.loadClass();
        RemoveRendering.noRenderingList();
    }
}