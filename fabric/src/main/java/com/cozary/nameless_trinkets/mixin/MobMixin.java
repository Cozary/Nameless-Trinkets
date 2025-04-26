package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    //Cancel mob current target.
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void preventHuskTargeting(LivingEntity target, CallbackInfo ci) {
        Mob attackerEntity = (Mob) (Object) this;
        if (!ModEvents.TargetingCallback.EVENT.invoker().canTarget(attackerEntity, target)) {
            ci.cancel();
        }
    }
}
