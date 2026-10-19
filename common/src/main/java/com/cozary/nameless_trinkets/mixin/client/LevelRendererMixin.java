package com.cozary.nameless_trinkets.mixin.client;

import com.cozary.nameless_trinkets.client.ClientTemporalShaderHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public class LevelRendererMixin {

    @Inject(method = "extract", at = @At("TAIL"))
    private void nameless_trinkets$onExtractLevel(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo ci) {
        ClientTemporalShaderHelper.render3DWorldWave(camera, deltaPartialTick);
    }
}
