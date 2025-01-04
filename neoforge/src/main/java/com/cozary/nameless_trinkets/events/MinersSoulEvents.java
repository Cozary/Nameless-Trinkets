package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.MinersSoul;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class MinersSoulEvents {

    @SubscribeEvent
    public static void playerBreakBlock(BlockEvent.BreakEvent event) {
        MinersSoul.Stats config = MinersSoul.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        Player player = event.getPlayer();

        if (!player.level().isClientSide && !player.getAbilities().instabuild) {
            var stack = AccessoriesCapability.get(player).getEquipped(ModItems.MINERS_SOUL.get());
            if (!stack.isEmpty()) {
                Level level = player.level();
                ItemStack itemStack = player.getMainHandItem();
                ItemStack fakeItemStack = new ItemStack(itemStack.getItem());
                ItemEnchantments enchantments = itemStack.get(DataComponents.ENCHANTMENTS);
                HolderLookup.Provider registries = level.registryAccess();
                HolderLookup<Enchantment> enchantmentRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);
                Holder<Enchantment> silkTouch = enchantmentRegistry.getOrThrow(Enchantments.SILK_TOUCH);
                Holder<Enchantment> fortune = enchantmentRegistry.getOrThrow(Enchantments.FORTUNE);
                if (enchantments != null && enchantments.getLevel(silkTouch) > 0) {
                    return;
                }
                int bonusLevel = enchantments != null ? enchantments.getLevel(fortune) : 0;
                FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((ServerLevel) level);
                fakePlayer.setItemSlot(EquipmentSlot.MAINHAND, fakeItemStack);
                ItemEnchantments.Mutable mutableEnchantments = new ItemEnchantments.Mutable(enchantments != null ? enchantments : ItemEnchantments.EMPTY);
                mutableEnchantments.upgrade(fortune, bonusLevel + config.extraLootingLevel);
                fakeItemStack.set(DataComponents.ENCHANTMENTS, mutableEnchantments.toImmutable());
                if (!(event.getState().getBlock() instanceof EntityBlock)) {
                    LootTable loot = level.getServer().reloadableRegistries().getLootTable(event.getState().getBlock().getLootTable().get());
                    LootParams context = new LootParams.Builder((ServerLevel) level)
                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getPos()))
                            .withParameter(LootContextParams.TOOL, fakeItemStack)
                            .withParameter(LootContextParams.BLOCK_STATE, event.getState())
                            .create(LootContextParamSets.BLOCK);
                    List<ItemStack> drops = loot.getRandomItems(context);
                    if (!drops.isEmpty()) {
                        drops.get(0).setCount(drops.get(0).getCount() - 1);
                    }
                    for (ItemStack drop : drops) {
                        ItemEntity itemEntity = new ItemEntity(level, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), drop);
                        itemEntity.setDefaultPickUpDelay();
                        itemEntity.setPos(Vec3.atCenterOf(event.getPos()));
                        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((level.random.nextFloat() - level.random.nextFloat()) * 0.1F, level.random.nextFloat() * 0.05F, (level.random.nextFloat() - level.random.nextFloat()) * 0.1F));
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
        }
    }


}
