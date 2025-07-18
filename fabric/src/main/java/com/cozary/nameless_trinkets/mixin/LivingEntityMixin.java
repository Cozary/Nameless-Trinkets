package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    //Attack player -> entity
    @Inject(method = "actuallyHurt", at = @At(value = "HEAD"))
    private void onDamageReduction(ServerLevel level, DamageSource damageSource, float amount, CallbackInfo ci) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        ModEvents.DamageModifyCallback.EVENT.invoker().onDamage(targetEntity, damageSource, amount);
    }

    //Damage player -> entity
    @Unique
    private DamageSource lastDamageSource;

    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    private void cacheDamageSource(ServerLevel level, DamageSource damageSource, float amount, CallbackInfo ci) {
        this.lastDamageSource = damageSource;
    }

    @ModifyArg(method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"
            ),
            index = 1
    )
    private float modifyDamageAmount(float originalAmount) {
        LivingEntity self = (LivingEntity)(Object)this;
        return ModEvents.DamageModifyCallback.EVENT.invoker().onDamage(self, lastDamageSource, originalAmount);
    }

    //On experience drop
    @Inject(method = "dropExperience", at = @At("HEAD"))
    private void onDropExperience(ServerLevel level, Entity entity, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        ModEvents.ExperienceDropModifierCallback.EVENT.invoker().onExperienceDrop(entity, livingEntity);
    }

}
