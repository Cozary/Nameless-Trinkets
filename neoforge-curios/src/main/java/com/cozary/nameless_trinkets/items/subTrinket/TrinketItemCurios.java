package com.cozary.nameless_trinkets.items.subTrinket;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class TrinketItemCurios<T extends TrinketsStats> extends TrinketItem<T> implements ICurioItem {

    public TrinketItemCurios(TrinketData trinketData) {
        super(trinketData);
    }
}
