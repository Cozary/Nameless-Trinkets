package com.cozary.nameless_trinkets.items.special;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class GlowingDust extends Item {

    public GlowingDust() {
        super(new Properties()
                .rarity(Rarity.RARE)
                .stacksTo(64));
    }
}
