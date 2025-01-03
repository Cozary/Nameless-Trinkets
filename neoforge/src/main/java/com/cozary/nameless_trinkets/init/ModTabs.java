package com.cozary.nameless_trinkets.init;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NamelessTrinkets.MOD_ID);

    public static final Supplier<CreativeModeTab> NAMELESS_TRINKETS_TAB = CREATIVE_MODE_TAB.register(NamelessTrinkets.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.nameless_trinkets"))
            .icon(() -> new ItemStack(ModItems.MYSTERIOUS_TRINKET.get()))
            .displayItems((parameters, output) -> ModItems.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
            .build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}




