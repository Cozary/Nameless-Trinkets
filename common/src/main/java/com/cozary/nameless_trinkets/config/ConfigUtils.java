package com.cozary.nameless_trinkets.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtils {
    private static final Gson serializer = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    public static void writeConfig(Path path, Object source) throws IOException {
        if (Files.exists(path)) {
            return;
        }

        try (Writer writer = Files.newBufferedWriter(path)) {
            serializer.toJson(source, writer);
            writer.flush();
        }
    }

    @Nullable
    public static <T> T readConfig(Path path, Type target) throws IOException {
        if (!Files.exists(path)) {
            return null;
        }

        try (Reader reader = Files.newBufferedReader(path)) {
            return serializer.fromJson(reader, target);
        }
    }
}

