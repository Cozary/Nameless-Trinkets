package com.cozary.nameless_trinkets.items.special;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static com.cozary.nameless_trinkets.utils.CommonUtils.itemId;

public class DubiousDust extends Item {

    public DubiousDust() {
        super(new Properties()
                .rarity(Rarity.UNCOMMON)
                .stacksTo(64)
                .setId(itemId("dubious_dust"))
        );
    }
}
