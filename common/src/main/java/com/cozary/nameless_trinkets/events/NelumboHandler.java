package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.NelumboBase;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class NelumboHandler {

    public static void applyWaterWalking(Player player, FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        boolean canStandOnFluid = cir.getReturnValue();

        if (player.isShiftKeyDown())
            return;

        NelumboBase.Stats config0 = NelumboBase.INSTANCE.getTrinketConfig();

        if (fluidState.is(FluidTags.WATER) && !player.isEyeInFluid(FluidTags.WATER) && config0.isEnable) {
            canStandOnFluid = true;
        }

        cir.setReturnValue(canStandOnFluid);
    }
}
