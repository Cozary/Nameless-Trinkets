package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.MinersSoul;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MinersSoulHandler {

    public static void playerBreakBlock(Player player, BlockState state, BlockPos pos, Level level) {
        MinersSoul.Stats config = MinersSoul.INSTANCE.getTrinketConfig();
        if (!config.isEnable || level.isClientSide || player == null || player.isSpectator()) return;

        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) return;

        var stack = accessories.getEquipped(ModItems.MINERS_SOUL.get());
        if (stack.isEmpty()) return;

        ItemStack itemStack = player.getMainHandItem();
        ItemStack fakeItemStack = new ItemStack(itemStack.getItem());
        ItemEnchantments enchantments = itemStack.get(DataComponents.ENCHANTMENTS);

        HolderLookup.Provider registries = level.registryAccess();
        HolderLookup<Enchantment> enchantmentRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);
        Holder<Enchantment> silkTouch = enchantmentRegistry.getOrThrow(Enchantments.SILK_TOUCH);
        Holder<Enchantment> fortune = enchantmentRegistry.getOrThrow(Enchantments.FORTUNE);

        if (enchantments != null && enchantments.getLevel(silkTouch) > 0) return;

        int bonusLevel = enchantments != null ? enchantments.getLevel(fortune) : 0;

        ItemEnchantments.Mutable mutableEnchantments = new ItemEnchantments.Mutable(
                enchantments != null ? enchantments : ItemEnchantments.EMPTY
        );
        mutableEnchantments.upgrade(fortune, bonusLevel + config.extraLootingLevel);
        fakeItemStack.set(DataComponents.ENCHANTMENTS, mutableEnchantments.toImmutable());

        if (!(state.getBlock() instanceof EntityBlock)) {
            LootTable loot = level.getServer().reloadableRegistries().getLootTable(state.getBlock().getLootTable());

            LootParams.Builder contextBuilder = new LootParams.Builder((ServerLevel) level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.TOOL, fakeItemStack)
                    .withParameter(LootContextParams.BLOCK_STATE, state);

            if (player != null) {
                contextBuilder.withOptionalParameter(LootContextParams.THIS_ENTITY, player);
            }

            List<ItemStack> drops = loot.getRandomItems(contextBuilder.create(LootContextParamSets.BLOCK));

            if (!drops.isEmpty()) {
                drops.get(0).setCount(drops.get(0).getCount() - 1);
            }

            for (ItemStack drop : drops) {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), drop);
                itemEntity.setDefaultPickUpDelay();
                itemEntity.setPos(Vec3.atCenterOf(pos));
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                        (level.random.nextFloat() - level.random.nextFloat()) * 0.1F,
                        level.random.nextFloat() * 0.05F,
                        (level.random.nextFloat() - level.random.nextFloat()) * 0.1F
                ));
                level.addFreshEntity(itemEntity);
            }
        }
    }
}

