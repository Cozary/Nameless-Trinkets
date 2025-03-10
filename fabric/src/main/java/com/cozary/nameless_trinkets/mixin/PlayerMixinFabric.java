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

    //Modifies destroy speed.
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void onGetDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        float baseSpeed = cir.getReturnValue();

        float newSpeed = ModEvents.BlockDestroySpeedCallback.EVENT.invoker().modifyDestroySpeed(player, state, baseSpeed);

        cir.setReturnValue(newSpeed);
    }

    //Modifies damage ? -> player
    @ModifyVariable(method = "actuallyHurt", at = @At(value = "HEAD"), argsOnly = true)
    private float handleHurt(float damageAmount, DamageSource damageSource) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;

        return ModEvents.DamageModifyCallback.EVENT.invoker().onDamage(targetEntity, damageSource, damageAmount);
    }
}
