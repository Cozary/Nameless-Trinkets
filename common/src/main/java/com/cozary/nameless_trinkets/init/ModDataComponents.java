package com.cozary.nameless_trinkets.init;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.TrinketBundleContents;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.component.BundleContents;


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

    public static final RegistryObject<DataComponentType<Float>> WOUNDBEARER_DAMAGE = DATA_COMPONENTS.register("woundbearer_damage",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );


    public static final RegistryObject<DataComponentType<TrinketBundleContents>> TRINKET_BUNDLE_CONTENTS = DATA_COMPONENTS.register("trinket_bundle_contents",
            () -> {
                return DataComponentType.<TrinketBundleContents>builder().persistent(TrinketBundleContents.CODEC).networkSynchronized(TrinketBundleContents.STREAM_CODEC).cacheEncoding().build();
    });

    public static void loadClass() {
    }
}
