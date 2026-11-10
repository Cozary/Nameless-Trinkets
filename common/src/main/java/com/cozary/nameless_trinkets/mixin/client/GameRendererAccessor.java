package com.cozary.nameless_trinkets.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("spectatedEntityPostEffect")
    void nameless_trinkets$setPostEffectId(Identifier id);

    @Accessor("spectatedEntityPostEffect")
    Identifier nameless_trinkets$getPostEffectId();

    @Accessor("spectatedEntityEffectActive")
    void nameless_trinkets$setEffectActive(boolean active);

    @Accessor("spectatedEntityEffectActive")
    boolean nameless_trinkets$isEffectActive();
}
