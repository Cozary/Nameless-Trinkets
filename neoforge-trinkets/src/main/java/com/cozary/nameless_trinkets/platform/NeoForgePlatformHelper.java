package com.cozary.nameless_trinkets.platform;

import com.cozary.nameless_trinkets.network.TemporalEffectPayload;
import com.cozary.nameless_trinkets.platform.services.IPlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public void sendTemporalEffectToPlayer(ServerPlayer player, int mode, boolean isReverse) {
        if (player != null) {
            PacketDistributor.sendToPlayer(player, new TemporalEffectPayload(mode, isReverse));
        }
    }
}