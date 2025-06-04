package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.items.trinkets.FateEmeraldBase;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;

public class MixinVillagerTradeHandler {


    public static void applyVillagerDiscounts(AbstractVillager villager, Player player) {
        FateEmeraldBase.Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        int discountBoost = config.discountBoost;

        for (MerchantOffer offer : villager.getOffers()) {
            int discount = Mth.floor((float) discountBoost * offer.getPriceMultiplier());
            offer.addToSpecialPriceDiff(-discount);
        }
    }


}
