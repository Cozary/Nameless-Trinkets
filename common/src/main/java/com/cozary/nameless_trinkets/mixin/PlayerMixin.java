package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.DarkNelumbo;
import com.cozary.nameless_trinkets.items.trinkets.Nelumbo;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.FluidTags;
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
            Nelumbo.Stats config0 = Nelumbo.INSTANCE.getTrinketConfig();
            DarkNelumbo.Stats config1 = DarkNelumbo.INSTANCE.getTrinketConfig();

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
    }
}

