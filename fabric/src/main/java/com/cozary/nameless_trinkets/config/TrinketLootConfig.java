package com.cozary.nameless_trinkets.config;

import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class TrinketLootConfig {

    @SerializedName("item")
    private final String itemId;

    @SerializedName("chance")
    private final double chance;

    @SerializedName("loot_tables")
    private final List<ResourceLocation> lootTables;

    public TrinketLootConfig(String itemId, double chance, List<ResourceLocation> lootTables) {
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

    public List<ResourceLocation> getLootTables() {
        return lootTables;
    }
}
