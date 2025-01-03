package com.cozary.nameless_trinkets.items.subTrinket;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;


public class TrinketData {
    private Item.Properties itemProperties;
    private Rarity itemRarity;
    private Class<? extends TrinketsStats> statsClass;

    public TrinketData() {
        this.itemProperties = new Item.Properties().stacksTo(1);
        this.itemRarity = Rarity.EPIC;
        this.statsClass = TrinketsStats.class;
    }

    public TrinketData(Item.Properties properties, Rarity rarity, Class<? extends TrinketsStats> config) {
        this.itemProperties = properties != null ? properties : new Item.Properties().stacksTo(1);
        this.itemRarity = rarity != null ? rarity : Rarity.EPIC;
        this.statsClass = config != null ? config : TrinketsStats.class;
    }

    public Item.Properties getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(Item.Properties itemProperties) {
        this.itemProperties = (itemProperties != null) ? itemProperties : new Item.Properties().stacksTo(1);
    }

    public Rarity getItemRarity() {
        return itemRarity;
    }

    public void setItemRarity(Rarity itemRarity) {
        this.itemRarity = (itemRarity != null) ? itemRarity : Rarity.EPIC;
    }

    public Class<? extends TrinketsStats> getStatsClass() {
        return statsClass;
    }

    public void setStatsClass(Class<? extends TrinketsStats> statsClass) {
        this.statsClass = (statsClass != null) ? statsClass : TrinketsStats.class;
    }

    public TrinketItemData<?> toConfigData() {
        try {
            return new TrinketItemData<>(statsClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Error creating TrinketItemData instance", e);
        }
    }

}
