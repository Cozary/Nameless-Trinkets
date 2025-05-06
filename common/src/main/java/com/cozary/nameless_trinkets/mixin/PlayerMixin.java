package com.cozary.nameless_trinkets.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class PlayerMixin {

    /*@Inject(method = "canStandOnFluid", at = @At("RETURN"), cancellable = true)
    public void onCanStandOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            NelumboBase.Stats config0 = NelumboBase.INSTANCE.getTrinketConfig();
            DarkNelumboBase.Stats config1 = DarkNelumboBase.INSTANCE.getTrinketConfig();

            var stack0 = AccessoriesCapability.get(player).getEquipped(ModItems.NELUMBO.get());
            var stack1 = AccessoriesCapability.get(player).getEquipped(ModItems.DARK_NELUMBO.get());

            boolean canStandOnFluid = cir.getReturnValue();

            if (!player.isShiftKeyDown()) {
                if (!stack0.isEmpty() && fluidState.is(FluidTags.WATER) && !player.isEyeInFluid(FluidTags.WATER) && config0.isEnable) {
                    canStandOnFluid = true;
                } else if (!stack1.isEmpty() && fluidState.is(FluidTags.LAVA) && !player.isEyeInFluid(FluidTags.LAVA) && config1.isEnable) {
                    player.clearFire();
                    canStandOnFluid = true;
                }
            }

            cir.setReturnValue(canStandOnFluid);
        }
    }*/
}

