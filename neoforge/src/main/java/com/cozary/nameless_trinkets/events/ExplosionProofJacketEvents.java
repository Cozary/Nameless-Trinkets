package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ExplosionProofJacket;
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

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class ExplosionProofJacketEvents {

    @SubscribeEvent
    public static void handleExplosionDamageReduction(LivingDamageEvent.Pre event) {

        ExplosionProofJacket.Stats config = ExplosionProofJacket.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(event.getEntity() instanceof Player player))
            return;

        if (!player.isSpectator()) {
            if (event.getEntity() == player) {
                Level world = player.level();
                ItemStack itemStack = Items.TNT.getDefaultInstance();

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.EXPLOSION_PROOF_JACKET.get());

                if (!stack.isEmpty()) {
                    if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
                        event.setNewDamage(event.getOriginalDamage() * (1 - config.blastDamagePercentageReduction) * 100);
                        BlockPos pos = player.blockPosition();
                        ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                        itementity.setDefaultPickUpDelay();
                        itementity.setInvulnerable(true);
                        world.addFreshEntity(itementity);
                    }
                }
            }
        }
    }

}
