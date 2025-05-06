package com.cozary.nameless_trinkets.config.common;

import com.google.gson.annotations.SerializedName;

public class CommonConfig {

    @SerializedName("getFragments")
    private boolean getFragments = true;

    @SerializedName("disableFOV")
    private boolean disableFOV = false;

    public boolean isGetFragments() {
        return getFragments;
    }

    public void setGetFragments(boolean value) {
        this.getFragments = value;
    }

    public boolean isDisableFOV() {
        return disableFOV;
    }

    public void setDisableFOV(boolean value) {
        this.disableFOV = value;
    }
}
