package com.cozary.nameless_trinkets.items.special;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class UltimateDust extends Item {

    public UltimateDust() {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "ultimate_dust")))
        );
    }


    @Override
    public boolean isFoil(@NotNull ItemStack p_77636_1_) {
        return true;
    }
}
