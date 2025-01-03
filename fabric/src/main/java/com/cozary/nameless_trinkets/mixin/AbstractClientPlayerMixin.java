package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
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

        if (ConfigurationHandler.GENERAL.disableFOV.get())
            return;

        var stack0 = AccessoriesCapability.get(player).getEquipped(ModItems.CRACKED_CROWN.get());
        var stack1 = AccessoriesCapability.get(player).getEquipped(ModItems.GODS_CROWN.get());
        var stack2 = AccessoriesCapability.get(player).getEquipped(ModItems.SCARAB_AMULET.get());
        var stack3 = AccessoriesCapability.get(player).getEquipped(ModItems.SPEED_FORCE.get());

        if (!stack0.isEmpty() || !stack1.isEmpty() || !stack2.isEmpty() || !stack3.isEmpty()) {
            cir.setReturnValue(1.0F);
        }
    }
}
