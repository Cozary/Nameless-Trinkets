package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ExplosionProofJacket;
import com.cozary.nameless_trinkets.util.TrinketUtils;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ExplosionProofJacketEvents {

    @SubscribeEvent
    public static void handleExplosionDamageReduction(LivingDamageEvent.Pre event) {

        if (!(event.getEntity() instanceof Player player))
            return;

        var stack = TrinketUtils.getEquippedTrinket(player, ModItems.EXPLOSION_PROOF_JACKET.get());

        if (stack.isEmpty())
            return;

        ExplosionProofJacketHandler.handleExplosionDamageReduction(player, event.getSource(), event.getOriginalDamage());
    }

}
