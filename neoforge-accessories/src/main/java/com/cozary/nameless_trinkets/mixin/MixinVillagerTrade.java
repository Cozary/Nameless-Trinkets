package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.mixinHandler.MixinVillagerTradeHandler;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class MixinVillagerTrade {


    @Inject(at = @At(value = "HEAD"), method = "startTrading")
    protected void badOffers(Player player, CallbackInfo ci) {
        MixinVillagerTradeHandler.applyVillagerDiscounts((AbstractVillager) (Object) this, player);
    }


}
