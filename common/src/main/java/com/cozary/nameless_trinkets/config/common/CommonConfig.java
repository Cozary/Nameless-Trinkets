package com.cozary.nameless_trinkets.config.common;

import com.google.gson.annotations.SerializedName;

public class CommonConfig {

    @SerializedName("getFragments")
    private boolean getFragments = true;

    @SerializedName("enableTrinketCrafting")
    private boolean enableTrinketCrafting = true;

    @SerializedName("globalLootMultiplier (0.0-1.0)")
    private double globalLootMultiplier = 0.0;

    public boolean isGetFragments() {
        return getFragments;
    }

    public boolean isEnableTrinketCrafting() {
        return enableTrinketCrafting;
    }

    public double getGlobalLootMultiplier() {
        return globalLootMultiplier;
    }
}
