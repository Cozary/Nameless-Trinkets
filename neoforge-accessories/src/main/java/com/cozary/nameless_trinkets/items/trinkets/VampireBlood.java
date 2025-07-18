package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class VampireBlood extends VampireBloodBase implements Accessory {

    public VampireBlood() {
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
    public void tick(ItemStack stack, SlotReference reference) {

        Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();

        if (!livingEntity.isSpectator()) {


            if (!stack.isEmpty() && livingEntity instanceof ServerPlayer) {

                if (livingEntity.level().isDay() && livingEntity.level().canSeeSky(livingEntity.blockPosition()) && !livingEntity.level().isClientSide) {

                    ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.FLAME, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1, 1D, 1D, 1D, 0.01);
                    livingEntity.hurt(livingEntity.damageSources().onFire(), (float) config.sunDamage);

                }
            }
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = VampireBloodBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "vampire_blood_attack_damage"),
                config.damageMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        CommonUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(reference.entity().getAttribute(Attributes.ATTACK_DAMAGE)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "vampire_blood_attack_damage"),
                        trinketConfig.damageMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}