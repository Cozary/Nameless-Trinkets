package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CreeperSense extends CreeperSenseBase implements ICurioItem {


    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = CreeperSenseBase.INSTANCE.getTrinketConfig();

        if (!(slotContext.entity() instanceof ServerPlayer player) || player.isSpectator()) {
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