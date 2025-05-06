package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class FateEmerald extends FateEmeraldBase implements ICurioItem {

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        config.resetTimerFlag = false;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return;
        }

        Level world = player.level();
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (config.backupTimeUntilUnequip == 0) {
            config.backupTimeUntilUnequip = config.timeUntilUnequip;
        }

        if (config.timeUntilUnequip > 0) {
            config.timeUntilUnequip--;
        }

        if (!world.isClientSide() && player.tickCount % 20 == 0) {
            player.causeFoodExhaustion(config.hungerExhaustionRate);
        }
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return true;
        }

        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (config.timeUntilUnequip <= 0) {
            config.timeUntilUnequip = config.backupTimeUntilUnequip;
            config.resetTimerFlag = true;
        }

        return config.resetTimerFlag || player.getAbilities().instabuild;
    }

}