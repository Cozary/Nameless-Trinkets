package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FateEmerald;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class MixinVillagerTrade {


    @Inject(at = @At(value = "HEAD"), method = "startTrading")
    protected void badOffers(Player player, CallbackInfo ci) {
        FateEmerald.Stats config = FateEmerald.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        int discountBoost = config.discountPercentage;

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.FATE_EMERALD.get());

        if (!stack.isEmpty()) {
            for (MerchantOffer merchantoffer : ((AbstractVillager) (Object) this).getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-Mth.floor((float) discountBoost * merchantoffer.getPriceMultiplier()));
            }

        }


    }


}
