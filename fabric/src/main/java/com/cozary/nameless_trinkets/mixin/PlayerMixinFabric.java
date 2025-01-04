package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixinFabric {

    //AmphibiousHands
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void onGetDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        float baseSpeed = cir.getReturnValue();

        float newSpeed = ModEvents.BlockDestroySpeedCallback.EVENT.invoker().modifyDestroySpeed(player, state, baseSpeed);

        cir.setReturnValue(newSpeed);
    }
}
