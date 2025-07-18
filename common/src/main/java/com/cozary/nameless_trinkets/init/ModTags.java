package com.cozary.nameless_trinkets.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.cozary.nameless_trinkets.NamelessTrinkets.MOD_ID;

public class ModTags {

    public static final TagKey<Item> NAMELESS_TRINKETS_TAG = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "nameless_trinkets_tag")
    );

    public static final TagKey<Item> RECYCLABLE_TRINKETS_TAG = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "recyclable_trinkets_tag")
    );

    public static void loadClass() {
    }
}
