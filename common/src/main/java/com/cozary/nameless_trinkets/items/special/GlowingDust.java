package com.cozary.nameless_trinkets.items.special;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class GlowingDust extends Item {

    public GlowingDust() {
        super(new Properties()
                .rarity(Rarity.RARE)
                .stacksTo(64)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "glowing_dust")))
        );
    }
}
