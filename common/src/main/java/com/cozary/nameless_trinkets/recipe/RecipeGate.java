package com.cozary.nameless_trinkets.recipe;


import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public final class RecipeGate {
    // Change this to match where you put the gated recipes:
    // data/nameless_trinkets/recipes/trinkets/*.json  ->  nameless_trinkets:trinkets/<file>
    private static final String PATH_PREFIX = "";

    private RecipeGate() {
    }

    public static void apply(MinecraftServer server) {
        boolean craftingEnabled = CommonConfigManager.getConfig().isEnableTrinketCrafting();
        if (craftingEnabled) return;

        RecipeManager rm = server.getRecipeManager();

        // Remove from the manager's internal "byName" map (or equivalent)
        Map<ResourceLocation, RecipeHolder<?>> byName = findRecipeByNameMap(rm);
        if (byName == null) {
            // If this happens, we can switch to a Mixin/AccessWidener approach.
            System.out.println("[Nameless Trinkets] RecipeGate: Could not locate RecipeManager recipe map; skipping.");
            return;
        }

        int removed = 0;
        Iterator<Map.Entry<ResourceLocation, RecipeHolder<?>>> it = byName.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ResourceLocation, RecipeHolder<?>> e = it.next();
            ResourceLocation id = e.getKey();

            if (NamelessTrinkets.MOD_ID.equals(id.getNamespace())) {
                it.remove();
                removed++;
            }
        }

        System.out.println("[Nameless Trinkets] RecipeGate: Removed " + removed + " gated trinket recipes (enableTrinketCrafting=false).");
    }

    @SuppressWarnings("unchecked")
    private static Map<ResourceLocation, RecipeHolder<?>> findRecipeByNameMap(RecipeManager rm) {
        // Mojang names vary a bit; try common candidates.
        // We only need the "byName" style map to stop crafting.
        String[] candidates = new String[]{
                "byName",      // common
                "recipes",     // sometimes used
                "f_44007_",    // obf-like (example)
        };

        for (String fieldName : candidates) {
            Map<ResourceLocation, RecipeHolder<?>> map = tryGetMapField(rm, fieldName);
            if (map != null) return map;
        }

        // If field names differ, try "any Map<ResourceLocation, RecipeHolder<?>>" by scanning fields.
        for (Field f : rm.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                Object val = f.get(rm);
                if (val instanceof Map<?, ?> m) {
                    // best-effort check: keys are ResourceLocation
                    Object firstKey = m.keySet().stream().findFirst().orElse(null);
                    if (firstKey instanceof ResourceLocation) {
                        return (Map<ResourceLocation, RecipeHolder<?>>) m;
                    }
                }
            } catch (Throwable ignored) {
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static Map<ResourceLocation, RecipeHolder<?>> tryGetMapField(RecipeManager rm, String fieldName) {
        try {
            Field f = rm.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object val = f.get(rm);
            if (val instanceof Map<?, ?>) return (Map<ResourceLocation, RecipeHolder<?>>) val;
        } catch (Throwable ignored) {
        }
        return null;
    }
}