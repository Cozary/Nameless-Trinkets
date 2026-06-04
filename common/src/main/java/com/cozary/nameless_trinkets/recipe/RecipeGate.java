package com.cozary.nameless_trinkets.recipe;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public final class RecipeGate {
    private static final List<String> EXEMPT_RECIPES = List.of(
            "mysterious_trinket",
            "trinket_bundle"
    );

    private RecipeGate() {
    }

    public static void apply(MinecraftServer server) {
        boolean craftingEnabled = CommonConfigManager.getConfig().isEnableTrinketCrafting();
        if (craftingEnabled) return;

        RecipeManager rm = server.getRecipeManager();
        int totalRemoved = 0;

        // 1. Scan fields directly in RecipeManager
        totalRemoved += scanAndClean(rm);

        // 2. Scan fields inside the 'recipes' field (RecipeMap) if it exists
        try {
            Field recipesField = getField(rm.getClass(), "recipes");
            if (recipesField != null) {
                Object recipeMap = recipesField.get(rm);
                if (recipeMap != null) {
                    totalRemoved += scanAndClean(recipeMap);
                }
            }
        } catch (Exception e) {
            // Ignore errors, they don't exist, trust me :)
        }

        if (totalRemoved > 0) {
            // Divide by 2 roughly because we remove from both byKey and byType
            //System.out.println("[Nameless Trinkets] RecipeGate: Disabled trinket crafting. Removed " + totalRemoved + " internal recipe references.");
        }
    }

    private static int scanAndClean(Object targetObj) {
        int removedCount = 0;
        for (Field f : targetObj.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                Object val = f.get(targetObj);

                if (val == null) continue;

                // Handle Map
                if (val instanceof Map<?, ?> map) {
                    if (map.isEmpty()) continue;

                    Map.Entry<?, ?> firstEntry = map.entrySet().iterator().next();
                    Object firstValue = firstEntry.getValue();

                    // Case A: Map<Key, RecipeHolder>
                    if (firstValue instanceof RecipeHolder) {
                        removedCount += cleanMapDirect(f, targetObj, (Map<Object, RecipeHolder<?>>) map);
                    }
                    // Case B: Map<Key, List<RecipeHolder>>
                    else if (firstValue instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof RecipeHolder) {
                        removedCount += cleanMapOfLists(f, targetObj, (Map<Object, List<RecipeHolder<?>>>) map);
                    }
                }
                // Handle  Multimap (via reflection to avoid direct dependency issues)
                else if (isMultimap(val.getClass())) {
                    removedCount += cleanMultimap(f, targetObj, val);
                }

            } catch (Exception ignored) {
            }
        }
        return removedCount;
    }

    private static boolean isMultimap(Class<?> clazz) {
        if (clazz.getName().contains("Multimap")) return true;
        for (Class<?> iface : clazz.getInterfaces()) {
            if (iface.getName().contains("Multimap")) return true;
        }
        return false;
    }

    private static int cleanMultimap(Field field, Object holder, Object multimapObj) {
        try {
            // 1. Get entries using reflection: Collection<Map.Entry<K, V>> entries()
            Method entriesMethod = multimapObj.getClass().getMethod("entries");
            Collection<?> entries = (Collection<?>) entriesMethod.invoke(multimapObj);

            // 2. Create new ArrayListMultimap
            Class<?> arrayListMultimapClass = Class.forName("com.google.common.collect.ArrayListMultimap");
            Method createMethod = arrayListMultimapClass.getMethod("create");
            Object newMultimap = createMethod.invoke(null);
            Method putMethod = arrayListMultimapClass.getMethod("put", Object.class, Object.class);

            int removed = 0;
            for (Object entryObj : entries) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entryObj;
                Object value = entry.getValue();

                boolean remove = false;
                if (value instanceof RecipeHolder<?> holderVal) {
                    if (shouldRemove(holderVal)) remove = true;
                }

                if (!remove) {
                    putMethod.invoke(newMultimap, entry.getKey(), entry.getValue());
                } else {
                    removed++;
                }
            }

            if (removed > 0) {
                field.set(holder, newMultimap);
            }
            return removed;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int cleanMapDirect(Field field, Object holder, Map<Object, RecipeHolder<?>> map) throws IllegalAccessException {
        Map<Object, RecipeHolder<?>> mutable = new HashMap<>(map);
        int removed = 0;

        Iterator<Map.Entry<Object, RecipeHolder<?>>> it = mutable.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, RecipeHolder<?>> e = it.next();
            if (shouldRemove(e.getValue())) {
                it.remove();
                removed++;
            }
        }

        if (removed > 0) {
            field.set(holder, mutable);
        }
        return removed;
    }

    private static int cleanMapOfLists(Field field, Object holder, Map<Object, List<RecipeHolder<?>>> map) throws IllegalAccessException {
        Map<Object, List<RecipeHolder<?>>> mutable = new HashMap<>();
        int removed = 0;

        for (Map.Entry<Object, List<RecipeHolder<?>>> entry : map.entrySet()) {
            List<RecipeHolder<?>> originalList = entry.getValue();
            List<RecipeHolder<?>> mutableList = new ArrayList<>(originalList);

            int listRemoved = 0;
            Iterator<RecipeHolder<?>> it = mutableList.iterator();
            while (it.hasNext()) {
                if (shouldRemove(it.next())) {
                    it.remove();
                    listRemoved++;
                }
            }

            if (listRemoved > 0) {
                removed += listRemoved;
            }
            mutable.put(entry.getKey(), mutableList);
        }

        if (removed > 0) {
            field.set(holder, mutable);
        }
        return removed;
    }

    private static boolean shouldRemove(RecipeHolder<?> holder) {
        Identifier id = holder.id().identifier();
        if (NamelessTrinkets.MOD_ID.equals(id.getNamespace())) {
            return !EXEMPT_RECIPES.contains(id.getPath());
        }
        return false;
    }

    private static Field getField(Class<?> clazz, String name) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}