package com.cozary.nameless_trinkets.config.common;

import com.cozary.nameless_trinkets.config.ConfigUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CommonConfigManager {

    private static final Path CONFIG_PATH = Path.of("config", "nameless_trinkets-common.json");
    private static CommonConfig config = new CommonConfig();

    public static void loadConfig() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            CommonConfig read = ConfigUtils.readConfig(CONFIG_PATH, CommonConfig.class);
            if (read != null) {
                config = read;
            } else {
                saveConfig();
            }
        } catch (IOException e) {
            System.err.println("Failed to load general config.");
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            ConfigUtils.writeConfig(CONFIG_PATH, config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save general config", e);
        }
    }

    public static CommonConfig getConfig() {
        return config;
    }
}
