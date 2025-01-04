package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.FourLeafClover;
import io.wispforest.accessories.api.AccessoriesCapability;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class FourLeafCloverEvents {

    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        FourLeafClover.Stats config = FourLeafClover.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if ((event.getEntity() instanceof Player))
            return;

        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide) {

                var stack = AccessoriesCapability.get(player).getEquipped(ModItems.FOUR_LEAF_CLOVER.get());

                if (!stack.isEmpty()) {
                    Level level = player.level();

                    LootTable loot = level.getServer().reloadableRegistries().getLootTable((event.getEntity().getType().getDefaultLootTable().get()));
                    LootParams context = new LootParams.Builder((ServerLevel) level)
                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getEntity().blockPosition()))
                            .withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
                            .withParameter(LootContextParams.DAMAGE_SOURCE, player.damageSources().playerAttack(player))
                            .create(LootContextParamSets.ENTITY);

                    List<ItemStack> drops = loot.getRandomItems(context);
                    for (int i = 1; i < (config.extraLoots); i++) {
                        for (ItemStack drop : drops) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), drop);
                            itementity.setDefaultPickUpDelay();
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((level.random.nextFloat() - level.random.nextFloat()) * 0.1F, level.random.nextFloat() * 0.05F, (level.random.nextFloat() - level.random.nextFloat()) * 0.1F));
                            level.addFreshEntity(itementity);
                        }
                    }

                }

            }
        }
    }
}
