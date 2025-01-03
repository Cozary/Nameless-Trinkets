package com.cozary.nameless_trinkets.items.subTrinket;


public class TrinketItemData<T extends TrinketsStats> {
    private final T config;

    public TrinketItemData(T config) {
        this.config = config;
    }

    public T getConfig() {
        return config;
    }
}
