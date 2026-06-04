package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CreeperSense extends CreeperSenseBase implements Trinket {

    public CreeperSense() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
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