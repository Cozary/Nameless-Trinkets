package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FateEmerald extends FateEmeraldBase implements Trinket {

    public FateEmerald() {
        super();
        }

    

    

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        config.resetTimerFlag = false;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
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
    public boolean canUnequip(ItemStack stack, SlotReference reference, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
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