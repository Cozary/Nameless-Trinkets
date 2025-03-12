package com.cozary.nameless_trinkets;


import com.cozary.nameless_trinkets.config.TrinketConfigs;
import com.cozary.nameless_trinkets.init.ModCodec;
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
        ModCodec.init(eventBus);

        container.registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);

    }

    private void setup(final FMLCommonSetupEvent event) {
        TrinketConfigs.loadClass();
        RemoveRendering.noRenderingList();
    }

    //Todo Can do in common i think? And extract it idk
    @EventBusSubscriber(modid = NamelessTrinkets.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemProperties.register((Item)ModItems.TRINKET_BUNDLE.get(), ResourceLocation.fromNamespaceAndPath("minecraft", "filled"), (stack, p_174626_, p_174627_, p_174628_) -> {
                return ((TrinketBundleContents) Objects.requireNonNull((TrinketBundleContents)stack.get((DataComponentType) ModDataComponents.TRINKET_BUNDLE_CONTENTS.get()))).weight().floatValue() * 64.0F;
            });
        }
    }

}