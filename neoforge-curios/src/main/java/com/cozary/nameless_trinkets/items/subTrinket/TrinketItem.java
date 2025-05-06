package com.cozary.nameless_trinkets.items.subTrinket;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class TrinketItem<T> extends Item implements ICurioItem {

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