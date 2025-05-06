package com.cozary.nameless_trinkets.mixin;

import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Villager.class)
public class MixinVillagerTrade {


    /*@Inject(at = @At(value = "HEAD"), method = "startTrading")
    protected void badOffers(Player player, CallbackInfo ci) {
        FateEmeraldBase.Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        int discountBoost = config.discountBoost;

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.FATE_EMERALD.get());

        if (!stack.isEmpty()) {
            for (MerchantOffer merchantoffer : ((AbstractVillager) (Object) this).getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-Mth.floor((float) discountBoost * merchantoffer.getPriceMultiplier()));
            }

        }


    }*/


}
