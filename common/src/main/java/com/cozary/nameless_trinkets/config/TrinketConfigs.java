package com.cozary.nameless_trinkets.config;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemData;
import com.google.gson.reflect.TypeToken;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrinketConfigs {
    private static final Path rootPath = Path.of("config", "nameless_trinkets");
    private static Date launchStartDate;

    public static Path getRootPath() {
        return rootPath;
    }

    public static Date getLaunchStartDate() {
        return launchStartDate;
    }

    public static void setLaunchStartDate(Date date) {
        launchStartDate = date;
    }

    public static void loadClass() {
        setLaunchStartDate(new Date());
        createTrinketConfigs();
    }

    private static void createTrinketConfigs() {
        ModItems.CREATIVE_TAB_ITEMS.forEach(registryObject -> {
            Item item = registryObject.get();

            if (item instanceof TrinketItem<?> trinketItem) {
                TrinketItemData<?> data = readConfig(trinketItem);

                if (data == null || data.getConfig() == null) {
                    String itemName = getItemName(trinketItem);
                    Path sourcePath = getRootPath().resolve(itemName + ".json");

                    if (Files.exists(sourcePath)) {
                        Path backupPath = getBackupPath(sourcePath);
                        try {
                            Files.createDirectories(backupPath);
                            Files.move(sourcePath, backupPath.resolve(itemName + ".json"));
                        } catch (IOException e) {
                            logError(e, "Failed to backup config for " + itemName);
                        }
                    }

                    try {
                        writeConfig(trinketItem);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to write config for " + itemName, e);
                    }
                    data = trinketItem.getTrinketData().toConfigData();
                }

                syncTrinketData(trinketItem, data);
            }
        });
    }

    private static void writeConfig(TrinketItem<?> trinketItem) throws IOException {
        Path path = getRootPath().resolve(getItemName(trinketItem) + ".json");
        Files.createDirectories(path.getParent());

        ConfigUtils.writeConfig(path, trinketItem.getTrinketData().toConfigData());
    }

    @Nullable
    private static TrinketItemData<?> readConfig(TrinketItem<?> trinketItem) {
        Path path = getRootPath().resolve(getItemName(trinketItem) + ".json");

        try {
            Object data = ConfigUtils.readConfig(path, TypeToken.getParameterized(TrinketItemData.class,
                    trinketItem.getTrinketData().getStatsClass()).getType());
            if (data instanceof TrinketItemData<?>) {
                return (TrinketItemData<?>) data;
            }
        } catch (Exception e) {
            logError(e, "Failed to read config for " + getItemName(trinketItem));
        }
        return null;
    }

    private static void syncTrinketData(TrinketItem<?> trinketItem, TrinketItemData<?> data) {
        TrinketData trinketItemData = trinketItem.getTrinketData();
        trinketItemData.setStatsClass(data.getConfig().getClass());
        trinketItem.setTrinketData(trinketItemData);
        trinketItem.setTrinketConfig(data.getConfig());
    }

    private static String getItemName(TrinketItem<?> trinketItem) {
        return ModItems.CREATIVE_TAB_ITEMS.stream()
                .filter(item -> item.get() == trinketItem)
                .findFirst()
                .map(item -> item.getId().getPath())
                .orElseThrow(() -> new IllegalArgumentException("Trinket (Item) not found: " + trinketItem));
    }

    private static Path getBackupPath(Path sourcePath) {
        return getRootPath()
                .resolve("backups")
                .resolve(new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(getLaunchStartDate()))
                .resolve(getRootPath().relativize(sourcePath).getParent());
    }

    private static void logError(Exception e, String message) {
        System.err.println(message);
        e.printStackTrace();
    }
}

