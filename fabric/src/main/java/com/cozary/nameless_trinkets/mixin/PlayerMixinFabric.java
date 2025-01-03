package com.cozary.nameless_trinkets.mixin;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.init.ModEvents;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.*;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(Player.class)
public class PlayerMixinFabric {

    //AmphibiousHands
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void onGetDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        float baseSpeed = cir.getReturnValue();

        float newSpeed = ModEvents.BlockDestroySpeedCallback.EVENT.invoker().modifyDestroySpeed(player, state, baseSpeed);

        cir.setReturnValue(newSpeed);
    }

    //BlazeNucleus
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float handleHurt(float damageAmount, DamageSource damageSource){
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        BlazeNucleus.Stats config = BlazeNucleus.INSTANCE.getTrinketConfig();
        if (!config.isEnable) return damageAmount;


        if (targetEntity instanceof Player player && !player.isSpectator()) {
            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BLAZE_NUCLEUS.get());

            if (!stack.isEmpty()) {
                if (config.fireDamageReductionPercentage < 100) {
                    if (damageSource.is(DamageTypes.LAVA) ||
                            damageSource.is(DamageTypes.ON_FIRE) ||
                            damageSource.is(DamageTypes.IN_FIRE) ||
                            damageSource.is(DamageTypes.HOT_FLOOR)) {

                        damageAmount *= (float) (1 - (config.fireDamageReductionPercentage / 100.0));
                    }
                }
            }
        }
        return damageAmount;
    }

    //Callus
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float handleCallusHurt(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        Callus.Stats config = Callus.INSTANCE.getTrinketConfig();

        if (targetEntity instanceof Player player && !player.isSpectator()) {
            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.CALLUS.get());
            if (!stack.isEmpty()) {
                if (isNullifiedDamageType(damageSource)) {
                    return 0.0f;
                }

                if (damageSource.is(DamageTypes.FALL)) {
                    damageAmount *= (float) (1 - (config.fallDamageReductionPercentage / 100.0));
                } else {
                    damageAmount *= (float) (1 - (config.generalDamageReductionPercentage / 100.0));
                }

                if (damageAmount <= 0.0f) {
                    return 0.0f;
                }
            }
        }

        return damageAmount;
    }

    private static boolean isNullifiedDamageType(DamageSource source) {
        return source.is(DamageTypes.CACTUS) ||
                source.is(DamageTypes.FALLING_ANVIL) ||
                source.is(DamageTypes.HOT_FLOOR) ||
                source.is(DamageTypes.SWEET_BERRY_BUSH);
    }

    //ExplosionProofJacket
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float handleExplosionProofHurt(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        ExplosionProofJacket.Stats config = ExplosionProofJacket.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.EXPLOSION_PROOF_JACKET.get());

            if (!stack.isEmpty()) {
                if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                    damageAmount *= (float) (1 - (config.blastDamagePercentageReduction / 100.0));

                    Level world = player.level();
                    ItemStack itemStack = Items.TNT.getDefaultInstance();
                    BlockPos pos = player.blockPosition();
                    ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setInvulnerable(true);
                    world.addFreshEntity(itemEntity);
                }
            }
        }

        return damageAmount;
    }

    //FracturedNullstone
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float reduceMagicDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        FracturedNullstone.Stats config = FracturedNullstone.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.FRACTURED_NULLSTONE.get());

            if (!stack.isEmpty()) {
                //haha DamageTypeTag Magic doesn't exist

                if (damageSource.type().msgId().equals("indirectMagic") || damageSource.type().msgId().equals("magic")) {
                    return damageAmount * config.magicDamageReduction;
                }
            }
        }

        return damageAmount;
    }

    //IceCube
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float negateFreezeDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        IceCube.Stats config = IceCube.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.ICE_CUBE.get());

            if (!stack.isEmpty()) {
                if (config.inmuneToFreezing) {
                    if (damageSource.is(DamageTypeTags.IS_FREEZING)) {
                        return 0;
                    }
                }
            }
        }

        return damageAmount;
    }

    //MissingPage
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float triggerDamageReflection(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        MissingPage.Stats config = MissingPage.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.MISSING_PAGE.get());
            Random random = new Random();

            if (!stack.isEmpty() && random.nextInt(100) <= config.activationPercentage) {

                AABB targetBox = new AABB(player.position(), player.position()).inflate(config.radiusInBlocks);

                List<LivingEntity> foundTarget =
                        targetEntity.level().getEntitiesOfClass(LivingEntity.class, targetBox, PlayerMixinFabric::isValidTarget);

                if (damageSource.getEntity() != null && !(damageSource.getEntity() instanceof Player) && !foundTarget.isEmpty() && !player.level().isClientSide) {

                    for (LivingEntity livingEntity : foundTarget) {
                        ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.SOUL, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 35, 1D, 1D, 1D, 0.1);
                        livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.percentageOfDamage / 100));
                    }
                }
            }
        }

        return damageAmount;
    }

    @Unique
    private static boolean isValidTarget(LivingEntity ent) {
        return (ent.getType() != EntityType.PLAYER) && (!ent.isInvulnerable());
    }

    //MoonStone
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float nullMoonFallDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        MoonStone.Stats config = MoonStone.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.MOON_STONE.get());

            if (!stack.isEmpty()) {
                if (damageSource.is(DamageTypeTags.IS_FALL)) {
                    return 0;
                }
            }
        }

        return damageAmount;
    }

    //RageMind
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float getEntity(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        RageMind.Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.RAGE_MIND.get());

            if (!stack.isEmpty()) {

                stack.getFirst().stack().set(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get(), BuiltInRegistries.ENTITY_TYPE.getKey(damageSource.getEntity().getType()).toString());

            }
        }

        return damageAmount;
    }

    //RageMind
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float reverseDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        ReverseCard.Stats config = ReverseCard.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {
            Random random = new Random();
            Entity sourceEntity = damageSource.getEntity();

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.REVERSE_CARD.get());

            if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToActivate) {

                if (sourceEntity != null && !(sourceEntity instanceof Player) && !player.level().isClientSide) {
                    ((ServerLevel) sourceEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.WITCH, sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(), 35, 1D, 1D, 1D, 0.1);
                    sourceEntity.hurt(sourceEntity.damageSources().generic(), damageAmount);
                }
            }
        }

        return damageAmount;
    }

    //SigilOfBaphomet
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float grantSigilImmunityOnDamage(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        SigilOfBaphomet.Stats config = SigilOfBaphomet.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {
            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.SIGIL_OF_BAPHOMET.get());

            if (!stack.isEmpty() && stack.getFirst().stack().getOrDefault(ModDataComponents.SIGIL_COUNT.get(), 0) > 0 && !player.level().isClientSide) {
                ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.ENCHANT, player.getX(), player.getY(), player.getZ(), 50, 0.5D, 1D, 0.5D, 0.1);
                return 0;
            }
        }

        return damageAmount;
    }

    //WoodenStick
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float cancelWoodenStick(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        WoodenStick.Stats config = WoodenStick.INSTANCE.getTrinketConfig();

        if (!config.isEnable) {
            return damageAmount;
        }

        if (targetEntity instanceof Player player && !player.isSpectator()) {
            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.WOODEN_STICK.get());

            if (!stack.isEmpty() && !player.level().isClientSide) {
                if (!player.getCooldowns().isOnCooldown(stack.getFirst().stack().getItem())) {
                    player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), (int) config.cooldown);
                    return 0;
                }
            }
        }

        return damageAmount;
    }
}
