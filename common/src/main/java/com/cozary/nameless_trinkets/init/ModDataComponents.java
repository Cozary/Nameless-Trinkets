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

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_ARMOR = DATA_COMPONENTS.register("dying_star_armor",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_ARMOR_TOUGHNESS = DATA_COMPONENTS.register("dying_star_armor_toughness",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_ATTACK_DAMAGE = DATA_COMPONENTS.register("dying_star_attack_damage",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_ATTACK_KNOCKBACK = DATA_COMPONENTS.register("dying_star_attack_knockback",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_ATTACK_SPEED = DATA_COMPONENTS.register("dying_star_attack_speed",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_BLOCK_BREAK_SPEED = DATA_COMPONENTS.register("dying_star_block_break_speed",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_BLOCK_INTERACTION_RANGE = DATA_COMPONENTS.register("dying_star_block_interaction_range",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_EXPLOSION_KNOCKBACK_RESISTANCE = DATA_COMPONENTS.register("dying_star_explosion_knockback_resistance",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_ENTITY_INTERACTION_RANGE = DATA_COMPONENTS.register("dying_star_entity_interaction_range",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_FALL_DAMAGE_MULTIPLIER = DATA_COMPONENTS.register("dying_star_fall_damage_multiplier",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_FLYING_SPEED = DATA_COMPONENTS.register("dying_star_flying_speed",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_KNOCKBACK_RESISTANCE = DATA_COMPONENTS.register("dying_star_knockback_resistance",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_LUCK = DATA_COMPONENTS.register("dying_star_luck",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_MAX_ABSORPTION = DATA_COMPONENTS.register("dying_star_max_absorption",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_MAX_HEALTH = DATA_COMPONENTS.register("dying_star_max_health",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_MINING_EFFICIENCY = DATA_COMPONENTS.register("dying_star_mining_efficiency",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_MOVEMENT_SPEED = DATA_COMPONENTS.register("dying_star_movement_speed",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_OXYGEN_BONUS = DATA_COMPONENTS.register("dying_star_oxygen_bonus",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_SNEAKING_SPEED = DATA_COMPONENTS.register("dying_star_sneaking_speed",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_SUBMERGED_MINING_SPEED = DATA_COMPONENTS.register("dying_star_submerged_mining_speed",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_SWEEPING_DAMAGE_RATIO = DATA_COMPONENTS.register("dying_star_sweeping_damage_ratio",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final RegistryObject<DataComponentType<Float>> DYING_STAR_WATER_MOVEMENT_EFFICIENCY = DATA_COMPONENTS.register("dying_star_water_movement_efficiency",
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
