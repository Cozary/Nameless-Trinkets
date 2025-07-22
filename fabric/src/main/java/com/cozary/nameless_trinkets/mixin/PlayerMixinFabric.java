package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixinFabric {

    //Modifies damage ? -> player
    @Unique
    private DamageSource lastDamageSource;

    //Modifies destroy speed.
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void onGetDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        float baseSpeed = cir.getReturnValue();

        float newSpeed = ModEvents.BlockDestroySpeedCallback.EVENT.invoker().modifyDestroySpeed(player, state, baseSpeed);

        cir.setReturnValue(newSpeed);
    }

    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    private void cacheDamageSource(ServerLevel level, DamageSource damageSource, float amount, CallbackInfo ci) {
        this.lastDamageSource = damageSource;
    }

    @ModifyArg(method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"
            ),
            index = 1
    )
    private float modifyDamageAmount(float originalAmount) {
        Player self = (Player) (Object) this;
        return ModEvents.DamageModifyCallback.EVENT.invoker().onDamage(self, lastDamageSource, originalAmount);
    }

}
