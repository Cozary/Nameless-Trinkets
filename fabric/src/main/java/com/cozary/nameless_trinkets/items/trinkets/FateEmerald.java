package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FateEmerald extends FateEmeraldBase implements Accessory {

    public FateEmerald() {
        super();
        AccessoryRegistry.register(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = FateEmeraldBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        config.resetTimerFlag = false;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) {
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
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) {
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