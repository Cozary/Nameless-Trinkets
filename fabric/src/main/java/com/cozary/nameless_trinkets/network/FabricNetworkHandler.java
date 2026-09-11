package com.cozary.nameless_trinkets.network;

import com.cozary.nameless_trinkets.client.ClientTemporalShaderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;

public class FabricNetworkHandler {

    public static void registerCommon() {
        PayloadTypeRegistry.clientboundPlay().register(TemporalEffectPayload.TYPE, TemporalEffectPayload.STREAM_CODEC);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientReceiver.register();
        }
    }

    private static class ClientReceiver {
        private static void register() {
            ClientPlayNetworking.registerGlobalReceiver(TemporalEffectPayload.TYPE, (payload, context) -> {
                context.client().execute(() -> {
                    if (payload.isReverse()) {
                        ClientTemporalShaderHelper.triggerReverseEnding();
                    } else {
                        ClientTemporalShaderHelper.setEffect(payload.mode());
                    }
                });
            });
        }
    }
}
