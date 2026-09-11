package com.cozary.nameless_trinkets.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("postEffectId")
    void nameless_trinkets$setPostEffectId(Identifier id);

    @Accessor("postEffectId")
    Identifier nameless_trinkets$getPostEffectId();

    @Accessor("effectActive")
    void nameless_trinkets$setEffectActive(boolean active);

    @Accessor("effectActive")
    boolean nameless_trinkets$isEffectActive();
}
