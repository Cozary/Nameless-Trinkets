package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.config.common.CommonConfigManager;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
    private void modifyFieldOfView(CallbackInfoReturnable<Float> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

        if (CommonConfigManager.getConfig().isDisableFOV())
            return;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }

        var stack0 = accessories.getEquipped(ModItems.CRACKED_CROWN.get());
        var stack1 = accessories.getEquipped(ModItems.GODS_CROWN.get());
        var stack2 = accessories.getEquipped(ModItems.SCARAB_AMULET.get());
        var stack3 = accessories.getEquipped(ModItems.SPEED_FORCE.get());

        if (!stack0.isEmpty() || !stack1.isEmpty() || !stack2.isEmpty() || !stack3.isEmpty()) {
            cir.setReturnValue(1.0F);
        }
    }
}
