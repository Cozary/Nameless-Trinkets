package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.DarkNelumboBase;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class DarkNelumboHandler {

    public static boolean blazeNucleusImmune(Player player, DamageSource source) {
        DarkNelumboBase.Stats config = DarkNelumboBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return false;

        if (player.isSpectator())
            return false;

        if (config.cancelLavaDamage) {
            return source.type().msgId().equals("lava");
        }

        return false;
    }

    public static void applyLavaWalking(Player player, FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        boolean canStandOnFluid = cir.getReturnValue();

        if (player.isShiftKeyDown())
            return;

        DarkNelumboBase.Stats config = DarkNelumboBase.INSTANCE.getTrinketConfig();

        if (fluidState.is(FluidTags.LAVA) && !player.isEyeInFluid(FluidTags.LAVA) && config.isEnable) {
            player.clearFire();
            canStandOnFluid = true;
        }

        cir.setReturnValue(canStandOnFluid);
    }
}
