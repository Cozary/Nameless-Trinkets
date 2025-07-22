package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ExplosionProofJacketBase extends TrinketItem<ExplosionProofJacketBase.Stats> {
    public static ExplosionProofJacketBase INSTANCE;

    public ExplosionProofJacketBase() {
        super(new TrinketData("explosion_proof_jacket", null, null, Stats.class));

        INSTANCE = this;
    }

    /**
     * At the moment is not available
     */
    /*
    @Override
    public boolean canBeHurtBy(DamageSource damageSource) {
        return !damageSource.is(DamageTypes.EXPLOSION);
    }
    */
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = ExplosionProofJacketBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.explosion_proof_jacket_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.explosion_proof_jacket_1", Component.translatable(String.format("%.1f", config.blastDamagePercentageReduction) + "%")).withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.explosion_proof_jacket_2").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float blastDamagePercentageReduction = 30.0F;
        public boolean isEnable = true;

    }

}