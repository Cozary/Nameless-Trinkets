package com.cozary.nameless_trinkets.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

public class UnknownFragmentEvent {

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            UnknownFragmentHandler.onBlockUse(player, level, hitResult.getBlockPos(), hand);
            return InteractionResult.PASS;
        });
    }

}
