package com.cozary.nameless_trinkets.mixinHandler;

import com.cozary.nameless_trinkets.items.trinkets.ElectricPaddleBase;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;

public class MixinBoatHandler {

    public static void applyBoostedBoatControl(AbstractBoat boat, Player player) {
        ElectricPaddleBase.Stats config = ElectricPaddleBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        float speed = config.boatSpeedMultiplierPercentage / 100;

        float f = 0.0F;

        if (boat.inputRight != boat.inputLeft && !boat.inputUp && !boat.inputDown) {
            f += 0.001F * speed;
        }

        boat.setYRot(boat.getYRot() + boat.deltaRotation);

        if (boat.inputUp) {
            f += 0.07F * speed;
        }

        if (boat.inputDown) {
            f -= 0.01F * speed;
        }

        boat.setDeltaMovement(boat.getDeltaMovement().add(
                Mth.sin(-boat.getYRot() * ((float) Math.PI / 180F)) * f,
                0.0D,
                Mth.cos(boat.getYRot() * ((float) Math.PI / 180F)) * f
        ));

        boat.setPaddleState(
                (boat.inputRight && !boat.inputLeft) || boat.inputUp,
                (boat.inputLeft && !boat.inputRight) || boat.inputUp
        );
    }
}
