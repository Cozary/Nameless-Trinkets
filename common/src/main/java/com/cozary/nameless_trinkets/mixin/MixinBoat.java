package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ElectricPaddle;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public class MixinBoat {

    @Inject(at = @At(value = "HEAD"), method = "controlBoat")
    protected void controlSpeed(CallbackInfo ci) {
        ElectricPaddle.Stats config = ElectricPaddle.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        float speed = config.boatSpeedMultiplierPercentage / 100;

        if ((((AbstractBoat) (Object) this).getFirstPassenger() instanceof Player player)) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ELECTRIC_PADDLE.get());

            if (((AbstractBoat) (Object) this).isVehicle() && !stack.isEmpty()) {
                float f = 0.0F;

                if (((AbstractBoat) (Object) this).inputRight != ((AbstractBoat) (Object) this).inputLeft && !((AbstractBoat) (Object) this).inputUp && !((AbstractBoat) (Object) this).inputDown) {
                    f += 0.001F * speed;
                }

                ((AbstractBoat) (Object) this).setYRot(((AbstractBoat) (Object) this).getYRot() + ((AbstractBoat) (Object) this).deltaRotation);
                if (((AbstractBoat) (Object) this).inputUp) {
                    f += 0.07F * speed;
                }

                if (((AbstractBoat) (Object) this).inputDown) {
                    f -= 0.01F * speed;
                }

                ((AbstractBoat) (Object) this).setDeltaMovement(((AbstractBoat) (Object) this).getDeltaMovement().add(Mth.sin(-((AbstractBoat) (Object) this).getYRot() * ((float) Math.PI / 180F)) * f, 0.0D, Mth.cos(((AbstractBoat) (Object) this).getYRot() * ((float) Math.PI / 180F)) * f));
                ((AbstractBoat) (Object) this).setPaddleState(((AbstractBoat) (Object) this).inputRight && !((AbstractBoat) (Object) this).inputLeft || ((AbstractBoat) (Object) this).inputUp, ((AbstractBoat) (Object) this).inputLeft && !((AbstractBoat) (Object) this).inputRight || ((AbstractBoat) (Object) this).inputUp);
            }
        }
    }
}
