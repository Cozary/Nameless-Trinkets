package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.mixinHandler.MixinBoatHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public class MixinBoat {

    @Inject(at = @At(value = "HEAD"), method = "controlBoat")
    protected void controlSpeed(CallbackInfo ci) {
        if (((Boat)(Object)this).getFirstPassenger() instanceof Player player) {

            MixinBoatHandler.applyBoostedBoatControl((Boat)(Object)this, player);
        }
    }
}
