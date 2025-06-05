package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.ExplosionProofJacket;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ExplosionProofJacketHandler {

    public static float handleExplosionDamageReduction(Player player, DamageSource damageSource, float originalDamage) {

        ExplosionProofJacket.Stats config = ExplosionProofJacket.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return originalDamage;

        if (!player.isSpectator()) {
            Level world = player.level();
            ItemStack itemStack = Items.TNT.getDefaultInstance();

            var accessories = AccessoriesCapability.get(player);

            if (accessories == null) {
                return originalDamage;
            }
            var stack = accessories.getEquipped(ModItems.EXPLOSION_PROOF_JACKET.get());
            if (!stack.isEmpty()) {
                if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                    BlockPos pos = player.blockPosition();
                    ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                    itementity.setDefaultPickUpDelay();
                    itementity.setInvulnerable(true);
                    world.addFreshEntity(itementity);
                    return originalDamage * (1 - (config.blastDamagePercentageReduction/100));
                }
            }
        }
        return originalDamage;
    }

}
