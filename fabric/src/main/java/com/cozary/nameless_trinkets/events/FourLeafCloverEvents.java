package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FourLeafClover;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FourLeafCloverEvents {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            FourLeafClover.Stats config = FourLeafClover.INSTANCE.getTrinketConfig();
            if (!config.isEnable)
                return;

            if ((entity instanceof Player))
                return;

            if (damageSource.getEntity() instanceof Player player) {
                if (!player.level().isClientSide) {

                    var stack = AccessoriesCapability.get(player).getEquipped(ModItems.FOUR_LEAF_CLOVER.get());

                    if (!stack.isEmpty()) {
                        Level level = player.level();

                        LootTable loot = level.getServer().reloadableRegistries().getLootTable((entity.getType().getDefaultLootTable()));
                        LootParams context = new LootParams.Builder((ServerLevel) level)
                                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.blockPosition()))
                                .withParameter(LootContextParams.THIS_ENTITY, entity)
                                .withParameter(LootContextParams.DAMAGE_SOURCE, player.damageSources().playerAttack(player))
                                .create(LootContextParamSets.ENTITY);

                        List<ItemStack> drops = loot.getRandomItems(context);
                        for (int i = 1; i < (config.extraLoots); i++) {
                            for (ItemStack drop : drops) {
                                ItemEntity itementity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), drop);
                                itementity.setDefaultPickUpDelay();
                                itementity.setDeltaMovement(itementity.getDeltaMovement().add((level.random.nextFloat() - level.random.nextFloat()) * 0.1F, level.random.nextFloat() * 0.05F, (level.random.nextFloat() - level.random.nextFloat()) * 0.1F));
                                level.addFreshEntity(itementity);
                            }
                        }

                    }

                }
            }
        });
    }
}
