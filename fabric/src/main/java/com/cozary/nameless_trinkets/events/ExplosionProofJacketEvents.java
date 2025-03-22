package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModEvents;
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

public class ExplosionProofJacketEvents {

    public static void register() {
        ModEvents.DamageModifyCallback.EVENT.register((targetEntity, damageSource, amount) -> {
            ExplosionProofJacket.Stats config = ExplosionProofJacket.INSTANCE.getTrinketConfig();

            if (!config.isEnable) {
                return amount;
            }

            if (targetEntity instanceof Player player && !player.isSpectator()) {

                var accessories = AccessoriesCapability.get(player);

                if (accessories == null) {
                    return amount;
                }
                var stack = accessories.getEquipped(ModItems.EXPLOSION_PROOF_JACKET.get());

                if (!stack.isEmpty()) {
                    if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                        amount *= (float) (1 - (config.blastDamagePercentageReduction / 100.0));

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

            return amount;
        });
    }
}
