package com.cozary.nameless_trinkets.init;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.codecs.AddSingleTrinketModifier;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;


public class ModCodec {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, NamelessTrinkets.MOD_ID);


    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> TRINKETS_LOOT =
            LOOT_MODIFIER_SERIALIZERS.register("trinkets_loot", () -> new AddSingleTrinketModifier(new LootItemCondition[0], Items.AIR).codec());

    public static void init(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
