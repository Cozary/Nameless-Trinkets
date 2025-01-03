package com.cozary.nameless_trinkets.items.subTrinket;

import io.wispforest.accessories.api.AccessoryItem;


public abstract class TrinketItem<T extends TrinketsStats> extends AccessoryItem {

    protected TrinketData trinketData;
    protected T trinketConfig;

    public TrinketItem(TrinketData trinketData) {
        super(trinketData.getItemRarity() == null
                ? trinketData.getItemProperties()
                : trinketData.getItemProperties().rarity(trinketData.getItemRarity()));
        setTrinketData(trinketData);
        try {
            setTrinketConfig(trinketData.getStatsClass().getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating config for Trinket Item", e);
        }
    }

    public TrinketData getTrinketData() {
        return trinketData;
    }

    public void setTrinketData(TrinketData trinketData) {
        this.trinketData = trinketData;
    }

    public T getTrinketConfig() {
        return trinketConfig;
    }

    public void setTrinketConfig(TrinketsStats trinketConfig) {
        this.trinketConfig = (T) trinketConfig;
    }
}