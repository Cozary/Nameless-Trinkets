package com.cozary.nameless_trinkets.config;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.item.Item;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrinketLootConfigsManager {

    private static final Path CONFIG_PATH = Path.of("config", "nameless_trinkets", "trinket_loot_configs");
    private static List<TrinketLootConfig> configs = new ArrayList<>();

    public static List<TrinketLootConfig> getConfigs() {
        return configs;
    }

    public static void loadConfigs() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                configs.clear();
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(CONFIG_PATH, "*.json")) {
                    for (Path path : stream) {
                        TrinketLootConfig config = ConfigUtils.readConfig(path, TrinketLootConfig.class);
                        configs.add(config);
                    }
                }
            } else {
                generateDefaultConfigs();
                saveConfigs();
            }
        } catch (Exception e) {
            NamelessTrinkets.LOG.error("Failed to load TrinketLootConfigs, generating defaults.", e);
            backupCorruptedConfigs();
            generateDefaultConfigs();
            saveConfigs();
        }
    }


    public static void saveConfigs() {
        try {
            Files.createDirectories(CONFIG_PATH);
            for (TrinketLootConfig config : configs) {
                String fileName = config.getItemId().replace(":", "_") + ".json";
                Path filePath = CONFIG_PATH.resolve(fileName);
                ConfigUtils.writeConfig(filePath, config);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save TrinketLootConfigs", e);
        }
    }


    private static void generateDefaultConfigs() {
        configs.clear();

        List<ResourceLocation> defaultLootTables = TrinketDataProvider.LOOT_TABLES.stream()
                .map(ResourceKey::location)
                .toList();

        TrinketDataProvider.getTrinketList().forEach(entry -> {
            double chance = (double) entry.get(0);
            Item item = (Item) entry.get(1);

            configs.add(new TrinketLootConfig(item.getDescriptionId(), chance, defaultLootTables));
        });
    }

    private static void backupCorruptedConfigs() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String timestamp = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(new Date());
                Path backupDir = CONFIG_PATH.getParent().resolve("backups").resolve(timestamp);
                Files.createDirectories(backupDir);

                try (DirectoryStream<Path> stream = Files.newDirectoryStream(CONFIG_PATH, "*.json")) {
                    for (Path path : stream) {
                        Files.move(path, backupDir.resolve(path.getFileName()));
                    }
                }
            }
        } catch (IOException e) {
            NamelessTrinkets.LOG.error("Failed to backup corrupted config files.", e);
        }
    }
}
