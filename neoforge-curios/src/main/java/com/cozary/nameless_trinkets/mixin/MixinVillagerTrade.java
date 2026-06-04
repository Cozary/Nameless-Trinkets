package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.mixinHandler.MixinVillagerTradeHandler;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class MixinVillagerTrade {


    @Inject(at = @At(value = "HEAD"), method = "startTrading")
    protected void badOffers(Player player, CallbackInfo ci) {

        var stack = TrinketUtils.getEquippedTrinket(player, ModItems.FATE_EMERALD.get());

        if (!stack.isEmpty())
            return;

        MixinVillagerTradeHandler.applyVillagerDiscounts((AbstractVillager) (Object) this, player);
    }


}
