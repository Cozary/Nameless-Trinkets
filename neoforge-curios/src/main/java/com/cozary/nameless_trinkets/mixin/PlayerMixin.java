package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.mixinHandler.PlayerMixinHandler;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class PlayerMixin {

    @Inject(method = "canStandOnFluid", at = @At("RETURN"), cancellable = true)
    public void onCanStandOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            var stackNelumbo = TrinketUtils.getEquippedTrinket(player, ModItems.NELUMBO.get());
            var stackDarkNelumbo = TrinketUtils.getEquippedTrinket(player, ModItems.DARK_NELUMBO.get());

            if (!stackNelumbo.isEmpty())
                PlayerMixinHandler.applyWaterWalking(player, fluidState, cir);

            if (!stackDarkNelumbo.isEmpty())
                PlayerMixinHandler.applyLavaWalking(player, fluidState, cir);
        }
    }
}

