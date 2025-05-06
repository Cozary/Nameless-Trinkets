package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Objects;

public class ScarabAmulet extends TrinketItem<ScarabAmulet.Stats> {
    public static ScarabAmulet INSTANCE;

    public ScarabAmulet() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = ScarabAmulet.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_1", config.speedMultiplierPercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_2").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.scarab_amulet_3").withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.add(Component.translatable(ChatFormatting.GRAY + "Suggested By: AzrouStone"));
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = ScarabAmulet.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "scarab_amulet_movement_speed"),
                config.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        if (livingEntity.level().getBlockState(getBlockPosBelowThatAffectsMyMovement(livingEntity)).is(BlockTags.SAND)) {
            EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);
        } else {
            EntityUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED)),
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "scarab_amulet_movement_speed"),
                            config.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    protected BlockPos getBlockPosBelowThatAffectsMyMovement(LivingEntity player) {
        return new BlockPos(player.getBlockX(), player.getBlockY() - 1, player.getBlockZ());
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        EntityUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "scarab_amulet_movement_speed"),
                        trinketConfig.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }


    public static class Stats extends TrinketsStats {
        public float speedMultiplierPercentage = 110.0f;
        public boolean isEnable = true;

    }

}