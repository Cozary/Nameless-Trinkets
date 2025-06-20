package com.cozary.nameless_trinkets.config.common;

import com.google.gson.annotations.SerializedName;

public class CommonConfig {

    @SerializedName("getFragments")
    private boolean getFragments = true;

    public boolean isGetFragments() {
        return getFragments;
    }

}
