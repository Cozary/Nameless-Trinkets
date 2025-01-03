package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ReverseCard;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Random;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ReverseCardEvent {

    @SubscribeEvent
    public static void reverseDamage(LivingDamageEvent.Post event) {
        ReverseCard.Stats config = ReverseCard.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        DamageSource source = event.getSource();
        Entity src = source.getEntity();
        Random random = new Random();
        if (event.getEntity() instanceof Player player) {

            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.REVERSE_CARD.get());

            if (!stack.isEmpty() && random.nextInt(100) <= config.chanceToActivate) {

                if (src != null && !(src instanceof Player) && !player.level().isClientSide) {
                    ((ServerLevel) src.getCommandSenderWorld()).sendParticles(ParticleTypes.WITCH, src.getX(), src.getY(), src.getZ(), 35, 1D, 1D, 1D, 0.1);
                    src.hurt(src.damageSources().generic(), event.getOriginalDamage());
                }
            }
        }
    }

}
