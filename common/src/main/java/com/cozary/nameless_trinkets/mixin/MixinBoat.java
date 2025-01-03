package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ElectricPaddle;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.util.Mth;
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
        ElectricPaddle.Stats config = ElectricPaddle.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        float speed = config.boatSpeedMultiplierPercentage / 100;

        if ((((Boat) (Object) this).getFirstPassenger() instanceof Player player)) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ELECTRIC_PADDLE.get());

            if (((Boat) (Object) this).isVehicle() && !stack.isEmpty()) {
                float f = 0.0F;

                if (((Boat) (Object) this).inputRight != ((Boat) (Object) this).inputLeft && !((Boat) (Object) this).inputUp && !((Boat) (Object) this).inputDown) {
                    f += 0.001F * speed;
                }

                ((Boat) (Object) this).setYRot(((Boat) (Object) this).getYRot() + ((Boat) (Object) this).deltaRotation);
                if (((Boat) (Object) this).inputUp) {
                    f += 0.07F * speed;
                }

                if (((Boat) (Object) this).inputDown) {
                    f -= 0.01F * speed;
                }

                ((Boat) (Object) this).setDeltaMovement(((Boat) (Object) this).getDeltaMovement().add(Mth.sin(-((Boat) (Object) this).getYRot() * ((float) Math.PI / 180F)) * f, 0.0D, Mth.cos(((Boat) (Object) this).getYRot() * ((float) Math.PI / 180F)) * f));
                ((Boat) (Object) this).setPaddleState(((Boat) (Object) this).inputRight && !((Boat) (Object) this).inputLeft || ((Boat) (Object) this).inputUp, ((Boat) (Object) this).inputLeft && !((Boat) (Object) this).inputRight || ((Boat) (Object) this).inputUp);
            }
        }
    }
}
