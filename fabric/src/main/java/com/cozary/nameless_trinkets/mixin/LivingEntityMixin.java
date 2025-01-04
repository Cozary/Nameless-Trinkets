package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.*;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    //BlazeNucleus
    @Inject(method = "actuallyHurt", at = @At(value = "HEAD"))
    private void onDamageReduction(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        BlazeNucleus.Stats config = BlazeNucleus.INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        Entity sourceEntity = damageSource.getEntity();
        if (sourceEntity instanceof Player attacker) {
            var stack = AccessoriesCapability.get(attacker).getEquipped(ModItems.BLAZE_NUCLEUS.get());

            if (!stack.isEmpty()) {
                targetEntity.setRemainingFireTicks(config.setEnemyInFireTicks);
                attacker.clearFire();
            }
        }

    }

    //ExperienceBattery
    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void onDropExperience(@Nullable Entity entity, CallbackInfo ci) {
        ExperienceBattery.Stats config = ExperienceBattery.INSTANCE.getTrinketConfig();
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (!config.isEnable) {
            return;
        }

        if (entity instanceof Player attackingPlayer) {
            var stack = AccessoriesCapability.get(attackingPlayer).getEquipped(ModItems.EXPERIENCE_BATTERY.get());

            if (stack.isEmpty() || livingEntity instanceof Player) {
                return;
            }

            int originalExperience = livingEntity.getExperienceReward((ServerLevel) livingEntity.level(), entity);
            int bonusExperience = (int) (originalExperience * config.experienceMultiplier);

            if (bonusExperience > 0) {
                livingEntity.level().addFreshEntity(new ExperienceOrb((ServerLevel) livingEntity.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), bonusExperience));
                ci.cancel();
            }
        }
    }

    //IceCube
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float applySlowEffect(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ICE_CUBE.get());

            if (!stack.isEmpty()) {
                MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, config.slownessTime, config.slownessLevel);
                targetEntity.addEffect(effectinstance);
            }
        }

        return damageAmount;
    }

    //PufferFishLiver
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float applyPoisonEffect(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        PufferFishLiver.Stats config = PufferFishLiver.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {

            Random random = new Random();
            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.PUFFER_FISH_LIVER.get());

            if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToApplyPoison) {
                MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.POISON, config.poisonTime, config.poisonLevel);
                targetEntity.addEffect(effectinstance);
            }
        }

        return damageAmount;
    }

    //RageMind
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float dealDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (damageSource.getEntity() instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.RAGE_MIND.get());

            if (!stack.isEmpty()) {

                if (stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

                    String entityString = stack.getFirst().stack().get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());

                    ResourceLocation resourceLocation = ResourceLocation.parse(entityString);

                    EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);

                    Entity entity = entityType.create(player.level());

                    Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) entity.getClass();

                    if (targetEntity.getClass() == classEntity) {
                        return damageAmount * config.damageMultiplier;
                    }
                }


            }
        }

        return damageAmount;
    }

}
