package com.cozary.nameless_trinkets.recipe;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.lang.reflect.Field;
import java.util.*;

public final class RecipeGate {
    private RecipeGate() {}

    public static void apply(MinecraftServer server) {
        if (CommonConfigManager.getConfig().isEnableTrinketCrafting()) {
            return;
        }

        RecipeManager rm = server.getRecipeManager();
        List<ResourceLocation> removedRecipes = new ArrayList<>();

        try {
            // 1. Handle 'byName' map: Map<ResourceLocation, RecipeHolder<?>>
            Field byNameField = findByNameField(rm);
            if (byNameField != null) {
                @SuppressWarnings("unchecked")
                Map<ResourceLocation, RecipeHolder<?>> originalMap = (Map<ResourceLocation, RecipeHolder<?>>) byNameField.get(rm);
                Map<ResourceLocation, RecipeHolder<?>> mutableMap = new HashMap<>(originalMap);

                Iterator<Map.Entry<ResourceLocation, RecipeHolder<?>>> it = mutableMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<ResourceLocation, RecipeHolder<?>> e = it.next();
                    if (shouldRemove(e.getValue())) {
                        removedRecipes.add(e.getKey());
                        it.remove();
                    }
                }
                byNameField.set(rm, mutableMap);
            } else {
                System.err.println("[Nameless Trinkets] RecipeGate: Could not find 'byName' recipe map.");
            }

            // 2. Handle 'byType' map: Multimap<RecipeType<?>, RecipeHolder<?>>
            Field byTypeField = findByTypeField(rm);
            if (byTypeField != null) {
                Object originalByType = byTypeField.get(rm);
                
                if (originalByType instanceof Multimap) {
                    @SuppressWarnings("unchecked")
                    Multimap<RecipeType<?>, RecipeHolder<?>> multimap = (Multimap<RecipeType<?>, RecipeHolder<?>>) originalByType;
                    
                    ImmutableListMultimap.Builder<RecipeType<?>, RecipeHolder<?>> builder = ImmutableListMultimap.builder();
                    
                    for (Map.Entry<RecipeType<?>, RecipeHolder<?>> entry : multimap.entries()) {
                        if (!shouldRemove(entry.getValue())) {
                            builder.put(entry);
                        }
                    }
                    
                    byTypeField.set(rm, builder.build());
                } else {
                     System.err.println("[Nameless Trinkets] RecipeGate: 'byType' field is not a Multimap as expected.");
                }
            } else {
                System.err.println("[Nameless Trinkets] RecipeGate: Could not find 'byType' recipe map.");
            }

            // Log results
            if (!removedRecipes.isEmpty()) {
                /*System.out.println("[Nameless Trinkets] RecipeGate: Disabled " + removedRecipes.size() + " recipes:");
                for (ResourceLocation id : removedRecipes) {
                    System.out.println(" - " + id);
                }*/
            } else {
                System.out.println("[Nameless Trinkets] RecipeGate: No recipes found to disable.");
            }

        } catch (Exception e) {
            System.err.println("[Nameless Trinkets] RecipeGate: Error disabling recipes.");
            e.printStackTrace();
        }
    }

    private static boolean shouldRemove(RecipeHolder<?> holder) {
        if (!NamelessTrinkets.MOD_ID.equals(holder.id().getNamespace())) {
            return false;
        }

        ItemStack result = holder.value().getResultItem(null); // Registry access can be null for simple item checks usually
        if (result.isEmpty()) return false;

        // Check if it is one of the dusts
        if (result.is(ModItems.DUBIOUS_DUST.get()) ||
            result.is(ModItems.GLOWING_DUST.get()) ||
            result.is(ModItems.ULTIMATE_DUST.get())) {
            return true;
        }

        // Check if it is in the tag
        return result.is(ModTags.NAMELESS_TRINKETS_TAG);
    }

    private static Field findByNameField(RecipeManager rm) {
        String[] candidates = {"byName", "recipes", "f_44007_"};
        for (String name : candidates) {
            try {
                Field f = rm.getClass().getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (Exception ignored) {}
        }
        
        // Fallback scan
        for (Field f : rm.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (Map.class.isAssignableFrom(f.getType())) {
                try {
                    Object val = f.get(rm);
                    if (val instanceof Map<?, ?> map && !map.isEmpty()) {
                        Object key = map.keySet().iterator().next();
                        if (key instanceof ResourceLocation) return f;
                    }
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private static Field findByTypeField(RecipeManager rm) {
        String[] candidates = {"byType", "recipesByType", "f_44008_"};
        for (String name : candidates) {
            try {
                Field f = rm.getClass().getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (Exception ignored) {}
        }
        
        // Fallback scan for Multimap
        for (Field f : rm.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (Multimap.class.isAssignableFrom(f.getType())) {
                return f;
            }
        }
        return null;
    }
}
