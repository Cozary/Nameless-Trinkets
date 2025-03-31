package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    //Attack player -> entity
    @Inject(method = "actuallyHurt", at = @At(value = "HEAD"))
    private void onDamageReduction(ServerLevel level, DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        ModEvents.DamageModifyCallback.EVENT.invoker().onDamage(targetEntity, damageSource, damageAmount);
    }

    //Damage player -> entity
    @ModifyVariable(method = "getDamageAfterArmorAbsorb", at = @At(value = "HEAD"), argsOnly = true)
    private float dealDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        return ModEvents.DamageModifyCallback.EVENT.invoker().onDamage(targetEntity, damageSource, damageAmount);

    }

    //On experience drop
    @Inject(method = "dropExperience", at = @At("HEAD"))
    private void onDropExperience(ServerLevel level, Entity entity, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        ModEvents.ExperienceDropModifierCallback.EVENT.invoker().onExperienceDrop(entity, livingEntity);
    }

}
