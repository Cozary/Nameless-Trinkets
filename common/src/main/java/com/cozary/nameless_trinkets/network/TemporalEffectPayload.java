package com.cozary.nameless_trinkets.network;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TemporalEffectPayload(int mode, boolean isReverse) implements CustomPacketPayload {
    public static final Type<TemporalEffectPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "temporal_effect"));
    public static final StreamCodec<FriendlyByteBuf, TemporalEffectPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, TemporalEffectPayload::mode,
            ByteBufCodecs.BOOL, TemporalEffectPayload::isReverse,
            TemporalEffectPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
