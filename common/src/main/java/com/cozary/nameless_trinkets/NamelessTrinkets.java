package com.cozary.nameless_trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import com.cozary.nameless_trinkets.utils.TrinketBundleContents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamelessTrinkets {

    public static final String MOD_ID = "nameless_trinkets";
    public static final String MOD_NAME = "Nameless Trinkets";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {

        ModItems.loadClass();
        ModTags.loadClass();
        ModDataComponents.loadClass();
    }
}