package com.cozary.nameless_trinkets.config.looTables;

import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.Identifier;

import java.util.List;

public class TrinketLootConfig {

    @SerializedName("item")
    private final String itemId;

    @SerializedName("chance")
    private final double chance;

    @SerializedName("loot_tables")
    private final List<Identifier> lootTables;

    public TrinketLootConfig(String itemId, double chance, List<Identifier> lootTables) {
        this.itemId = itemId;
        this.chance = chance;
        this.lootTables = lootTables;
    }

    public String getItemId() {
        return itemId;
    }

    public double getChance() {
        return chance;
    }

    public List<Identifier> getLootTables() {
        return lootTables;
    }
}
