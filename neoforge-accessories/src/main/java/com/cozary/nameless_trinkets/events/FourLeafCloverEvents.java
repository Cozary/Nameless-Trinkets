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
        if ((event.getEntity() instanceof Player))
            return;

        if (event.getSource().getEntity() instanceof Player player) {
           FourLeafCloverHandler.entityKilled(player, event.getEntity());
        }
    }
}
