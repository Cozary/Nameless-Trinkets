package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.IceCube;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static net.neoforged.neoforge.event.EventHooks.onBlockPlace;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class IceCubeEvents {

    @SubscribeEvent
    public static void applySlowEffect(LivingDamageEvent.Post event) {

        if (!(event.getSource().getEntity() instanceof Player))
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();

        if (src instanceof Player player) {

            IceCubeHandler.applySlowEffect(player, (LivingEntity) event.getSource().getEntity());

        }
    }

    @SubscribeEvent
    public static void negateFreezeDamage(LivingIncomingDamageEvent event) {

        if (event.getEntity() instanceof Player player) {

                            event.setCanceled(IceCubeHandler.negateFreezeDamage(player, event.getSource()));

                        }
    }


}
