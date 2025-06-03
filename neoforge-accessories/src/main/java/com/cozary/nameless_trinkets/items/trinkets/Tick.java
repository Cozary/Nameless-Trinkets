package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class Tick extends TickBase implements Accessory {

    public Tick(){
        super();
        AccessoriesAPI.registerAccessory(this, this);
    }


    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        Player player = (Player) livingEntity;
        return livingEntity.isOnFire() || player.getAbilities().instabuild;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {

        Stats config = TickBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof ServerPlayer player) || player.isSpectator())
            return;


        if (!stack.isEmpty()) {

            AABB targetBox = new AABB(player.position(), player.position()).inflate(config.rangeToActivate);
            List<LivingEntity> foundTarget =
                    player.level().getEntitiesOfClass(LivingEntity.class, targetBox, TickBase::isValidTarget);

            if (!foundTarget.isEmpty()) {
                for (LivingEntity livingEntity : foundTarget) {

                    if ((livingEntity.getMaxHealth() > 50 && livingEntity.getHealth() > livingEntity.getMaxHealth() / 2) && player.getHealth() >= 5) {

                        livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.entityDamagePercentage / 100));
                        player.hurt(livingEntity.damageSources().generic(), player.getMaxHealth() * (config.playerDamagePercentage / 100));

                    } else if ((livingEntity.getMaxHealth() > 50 && livingEntity.getHealth() > livingEntity.getMaxHealth() / 2) && player.getFoodData().getFoodLevel() >= 5) {

                        livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.entityDamagePercentage / 100));
                        player.causeFoodExhaustion(player.getFoodData().getFoodLevel() * (config.playerHungerPercentage / 100));
                    }
                }
            }
        }
    }


}