package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.items.trinkets.LunarCrestBase;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.dimension.DimensionType;

public class LunarCrestHandler {

    public static double getBonusPercentage(Level level, BlockPos pos) {
        LunarCrestBase.Stats config = LunarCrestBase.INSTANCE.getTrinketConfig();
        if (level == null || !level.isDarkOutside())
            return 0.0;

        MoonPhase moonPhase = level.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, pos);
        float moonBrightness = DimensionType.MOON_BRIGHTNESS_PER_PHASE[moonPhase.index()];
        return config.baseMoonDamagePercentage + (config.maxMoonDamagePercentage - config.baseMoonDamagePercentage) * (double) moonBrightness;
    }

    public static double getCurrentClientBonus() {
        Level level = Minecraft.getInstance().level;
        BlockPos pos = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.blockPosition() : BlockPos.ZERO;
        return getBonusPercentage(level, pos);
    }

    public static float dealDamage(LivingEntity targetEntity, DamageSource damageSource, float originalAmount, Player player, ItemStack stack) {
        LunarCrestBase.Stats config = LunarCrestBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalAmount;

        Level level = player.level();
        if (level.isClientSide() || !level.isDarkOutside())
            return originalAmount;

        double bonusPercentage = getBonusPercentage(level, player.blockPosition());
        return originalAmount * (1.0f + (float) (bonusPercentage / 100.0));
    }

    public static float dealWolfDamage(LivingEntity targetEntity, DamageSource damageSource, float originalAmount, Wolf wolf, Player owner, ItemStack stack) {
        LunarCrestBase.Stats config = LunarCrestBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalAmount;

        Level level = wolf.level();
        if (level.isClientSide() || !level.isDarkOutside())
            return originalAmount;

        return originalAmount * (1.0f + (float) (config.wolfDamageMultiplierPercentage / 100.0));
    }
}
