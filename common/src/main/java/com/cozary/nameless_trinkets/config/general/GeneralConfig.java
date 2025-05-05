package com.cozary.nameless_trinkets.config.general;

import com.google.gson.annotations.SerializedName;

public class GeneralConfig {

    @SerializedName("getFragments")
    private boolean getFragments = true;

    @SerializedName("disableFOV")
    private boolean disableFOV = false;

    public boolean isGetFragments() {
        return getFragments;
    }

    public boolean isDisableFOV() {
        return disableFOV;
    }

    public void setGetFragments(boolean value) {
        this.getFragments = value;
    }

    public void setDisableFOV(boolean value) {
        this.disableFOV = value;
    }
}
