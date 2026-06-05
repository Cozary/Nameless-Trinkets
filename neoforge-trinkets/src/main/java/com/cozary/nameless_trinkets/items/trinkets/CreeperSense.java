package com.cozary.nameless_trinkets.items.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CreeperSense extends CreeperSenseBase implements TrinketCallback {

    public CreeperSense() {
        super();
    }


    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        Stats config = CreeperSenseBase.INSTANCE.getTrinketConfig();

        if (!(entity instanceof ServerPlayer player) || player.isSpectator()) {
            return;
        }

        if (!config.isEnable) {
            return;
        }

        BlockPos posAbove = player.blockPosition().above().above();
        BlockState blockAbove = player.level().getBlockState(posAbove);

        if (player.getCooldowns().isOnCooldown(stack.getItem().getDefaultInstance()))
            return;

        if (blockAbove.isAir() && player.isCrouching()) {
            player.getCooldowns().addCooldown(stack.getItem().getDefaultInstance(), config.cooldownInTicks);
            player.level().explode(null, player.getX(), player.getY(), player.getZ(), config.explosionLevel, Level.ExplosionInteraction.NONE);
            player.setShiftKeyDown(false);
        }

    }

}
