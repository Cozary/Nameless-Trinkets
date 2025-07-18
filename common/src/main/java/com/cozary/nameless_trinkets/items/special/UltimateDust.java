package com.cozary.nameless_trinkets.items.special;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import static com.cozary.nameless_trinkets.utils.CommonUtils.itemId;

public class UltimateDust extends Item {

    public UltimateDust() {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .stacksTo(64)
                .setId(itemId("ultimate_dust"))
        );
    }

    @Override
    public boolean isFoil(@NotNull ItemStack p_77636_1_) {
        return true;
    }
}
