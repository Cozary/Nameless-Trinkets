package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.mixinHandler.MixinBoatHandler;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
public class MixinBoat {

    @Inject(at = @At("TAIL"), method = "controlBoat")
    protected void controlSpeed(CallbackInfo ci) {
        AbstractBoat boat = (AbstractBoat)(Object) this;

        if (boat.getFirstPassenger() instanceof Player player) {
            var stack = TrinketUtils.getEquippedTrinket(player, ModItems.ELECTRIC_PADDLE.get());

            if (stack.isEmpty())
                return;

            if (boat instanceof Boat vanillaBoat) {
                MixinBoatHandler.applyBoostedBoatControl(vanillaBoat, player);
            } else if (boat instanceof ChestBoat chestBoat) {
                MixinBoatHandler.applyBoostedBoatControl(chestBoat, player);
            } else {
                MixinBoatHandler.applyBoostedBoatControl(boat, player);
            }
        }
    }
}
