package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ScarabAmulet;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    //ScarabAmulet
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void preventHuskTargeting(LivingEntity target, CallbackInfo ci) {
        Mob attackerEntity = (Mob) (Object) this;
        ScarabAmulet.Stats config = ScarabAmulet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (target instanceof Player player) {
            if (!player.level().isClientSide) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SCARAB_AMULET.get());

                if (!stack.isEmpty() && attackerEntity instanceof Husk) {
                    ci.cancel();
                }
            }

        }
    }
}
