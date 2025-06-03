package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CreeperSense extends CreeperSenseBase implements Accessory {

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = CreeperSenseBase.INSTANCE.getTrinketConfig();

        if (!(reference.entity() instanceof ServerPlayer player) || player.isSpectator()) {
            return;
        }

        if (!config.isEnable) {
            return;
        }

        BlockPos posAbove = player.blockPosition().above().above();
        BlockState blockAbove = player.level().getBlockState(posAbove);

        if (player.getCooldowns().isOnCooldown(stack.getItem()))
            return;

        if (blockAbove.isAir() && player.isCrouching()) {
            player.getCooldowns().addCooldown(stack.getItem(), config.cooldownInTicks);
            player.level().explode(null, player.getX(), player.getY(), player.getZ(), config.explosionLevel, Level.ExplosionInteraction.NONE);
            player.setShiftKeyDown(false);
        }

    }

}