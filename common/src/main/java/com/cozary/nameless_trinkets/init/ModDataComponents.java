package com.cozary.nameless_trinkets.init;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;


public class ModDataComponents {

    public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENTS = RegistrationProvider.get(Registries.DATA_COMPONENT_TYPE, NamelessTrinkets.MOD_ID);

    public static final RegistryObject<DataComponentType<Integer>> BROKEN_ANKH_COOLDOWN = DATA_COMPONENTS.register("broken_ankh_cooldown",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<String>> RAGE_MIND_REVENGE_TARGET = DATA_COMPONENTS.register("rage_mind_revenge_target",
            () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Integer>> SIGIL_COUNT = DATA_COMPONENTS.register("sigil_count",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static void loadClass() {
    }
}
