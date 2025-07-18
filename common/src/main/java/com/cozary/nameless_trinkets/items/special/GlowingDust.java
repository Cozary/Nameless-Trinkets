package com.cozary.nameless_trinkets.items.special;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static com.cozary.nameless_trinkets.utils.CommonUtils.itemId;

public class GlowingDust extends Item {

    public GlowingDust() {
        super(new Properties()
                .rarity(Rarity.RARE)
                .stacksTo(64)
                .setId(itemId("glowing_dust"))
        );
    }
}
