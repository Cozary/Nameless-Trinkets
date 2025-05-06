package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamelessTrinkets {

    public static final String MOD_ID = "nameless_trinkets";
    public static final String MOD_NAME = "Nameless Trinkets";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {

        /*NamelessTrinkets.LOG.info("ENV{} ", Services.PLATFORM.getEnvironmentName());
        NamelessTrinkets.LOG.info("PLAT{} ", Services.PLATFORM.getPlatformName());
        NamelessTrinkets.LOG.info("LOADER NEO{} ", Services.PLATFORM.isModLoaded("neoforge"));
        NamelessTrinkets.LOG.info("LOADER FAB{} ", Services.PLATFORM.isModLoaded("fabric"));*/

        ModTags.loadClass();
        ModDataComponents.loadClass();
    }
}