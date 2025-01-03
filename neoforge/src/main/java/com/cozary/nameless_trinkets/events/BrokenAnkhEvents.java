package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh.getCooldown;
import static com.cozary.nameless_trinkets.items.trinkets.BrokenAnkh.setCooldown;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class BrokenAnkhEvents {

    @SubscribeEvent
    public static void stopDeath(LivingDeathEvent event) {
        BrokenAnkh.Stats config = BrokenAnkh.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getEntity() instanceof Player player && !player.isSpectator()) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BROKEN_ANKH.get());

            if (!stack.isEmpty() && player.isDeadOrDying() && !player.getCooldowns().isOnCooldown(stack.getFirst().stack().getItem()) && !player.level().isClientSide) {
                ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
                ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY(), player.getZ(), 50, 1D, 1D, 1D, 0.1);
                ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY(), player.getZ(), 100, 1D, 1D, 1D, 0.1);
                event.setCanceled(true);
                player.setHealth(2.0F);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), config.cooldown);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BROKEN_ANKH.get());

        if (!stack.isEmpty()) {
            player.getCooldowns().addCooldown(stack.getFirst().stack().getItem(), getCooldown(stack.getFirst().stack()));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        BrokenAnkh.Stats config = BrokenAnkh.INSTANCE.getTrinketConfig();

        Player player = event.getEntity();

        var stack = AccessoriesCapability.get(player).getEquipped(ModItems.BROKEN_ANKH.get());

        if (stack.isEmpty())
            return;

        setCooldown(stack.getFirst().stack(), (int) (player.getCooldowns().getCooldownPercent(stack.getFirst().stack().getItem(), 0) * config.cooldown));
    }
}
