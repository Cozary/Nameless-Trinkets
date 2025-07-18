package com.cozary.nameless_trinkets.utils;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;


public class CommonUtils {

    public static void applyAttributeModifier(AttributeInstance instance, AttributeModifier modifier) {
        if (!instance.hasModifier(modifier.id()))
            instance.addTransientModifier(modifier);
    }

    public static void removeAttributeModifier(AttributeInstance instance, AttributeModifier modifier) {
        if (instance.hasModifier(modifier.id()))
            instance.removeModifier(modifier.id());
    }

    public static ResourceKey<Item> itemId(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, name));
    }

}
