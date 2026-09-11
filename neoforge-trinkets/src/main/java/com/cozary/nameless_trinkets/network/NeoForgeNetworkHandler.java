package com.cozary.nameless_trinkets.network;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.client.ClientTemporalShaderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NeoForgeNetworkHandler {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NamelessTrinkets.MOD_ID).versioned("1.0.0");

        registrar.playToClient(
                TemporalEffectPayload.TYPE,
                TemporalEffectPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> {
                    if (payload.isReverse()) {
                        ClientTemporalShaderHelper.triggerReverseEnding();
                    } else {
                        ClientTemporalShaderHelper.setEffect(payload.mode());
                    }
                })
        );
    }
}
