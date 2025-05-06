package com.cozary.nameless_trinkets.items.subTrinket;


import io.wispforest.accessories.api.AccessoryItem;

public class TrinketItemAccessories<T extends TrinketsStats> extends AccessoryItem {

    private final TrinketItem<T> trinket;

    public TrinketItemAccessories(TrinketData trinketData) {
        super(trinketData.getItemProperties());
        this.trinket = new TrinketItem<>(trinketData) {
        };
    }


    public TrinketItem<T> getTrinket() {
        return trinket;
    }

    public T getTrinketConfig() {
        return trinket.getTrinketConfig();
    }

    public TrinketData getTrinketData() {
        return trinket.getTrinketData();
    }
}
