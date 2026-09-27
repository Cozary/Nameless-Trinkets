package com.cozary.nameless_trinkets.events;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class AwakenedHourglassHandler {

    public static void tick(LivingEntity entity, ItemStack stack) {
        ShatteredHourglassHandler.tick(entity, stack);
    }

    public static void cleanSession(UUID playerUUID, MinecraftServer server) {
        ShatteredHourglassHandler.cleanSession(playerUUID, server);
    }
}
