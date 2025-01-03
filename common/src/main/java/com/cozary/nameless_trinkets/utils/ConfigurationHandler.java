package com.cozary.nameless_trinkets.utils;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ConfigurationHandler {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ModConfigSpec spec = BUILDER.build();

    public static class General {
        public final ModConfigSpec.IntValue slotProbability;
        public final ModConfigSpec.BooleanValue speedForceParticles;
        public final ModConfigSpec.BooleanValue getFragments;
        public final ModConfigSpec.IntValue trinketSlots;
        public final ModConfigSpec.BooleanValue disableFOV;
        public final ModConfigSpec.IntValue startingSlotQuantity;

        public General(ModConfigSpec.Builder builder) {
            builder.push("New Trinket Slot Probability");
            slotProbability = builder.defineInRange("newSlotProbability", 10, 0, 100);
            builder.pop();

            builder.push("Speed Force Particles");
            speedForceParticles = builder.define("showParticles", true);
            builder.pop();

            builder.push("Fragments from Trinket Destruction");
            getFragments = builder.define("getFragments", true);
            builder.pop();

            builder.push("Trinket Slot Settings");
            trinketSlots = builder.defineInRange("maxTrinketSlots", 2, 0, 99);
            startingSlotQuantity = builder.defineInRange("startingSlotQuantity", 1, 0, 99);
            builder.pop();

            builder.push("FOV Disable Settings");
            disableFOV = builder.define("disableFOV", false);
            builder.pop();
        }
    }
}
