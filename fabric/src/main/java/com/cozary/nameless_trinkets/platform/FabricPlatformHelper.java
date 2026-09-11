package com.cozary.nameless_trinkets.platform;

import com.cozary.nameless_trinkets.network.TemporalEffectPayload;
import com.cozary.nameless_trinkets.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void sendTemporalEffectToPlayer(ServerPlayer player, int mode, boolean isReverse) {
        if (player != null) {
            ServerPlayNetworking.send(player, new TemporalEffectPayload(mode, isReverse));
        }
    }
}
