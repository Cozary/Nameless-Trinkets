package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItemCurios;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class FragileCloud extends TrinketItemCurios<FragileCloudBase.Stats> {
    private static final AttributeModifier SLOW_FALLING = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "slow_falling"), -0.07, AttributeModifier.Operation.ADD_VALUE); // Add -0.07 to 0.08 so we get the vanilla default of 0.01
    public static FragileCloudBase INSTANCE;

    public FragileCloud() {
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
    public void curioTick(SlotContext slotContext, ItemStack stack) {

        Stats config = FragileCloudBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) return;

        if (!(slotContext.entity() instanceof Player player)) return;

        if (!player.level().isClientSide && !player.isSpectator()) {

            var stack0 = true; /*AccessoriesCapability.get(player).getEquipped(ModItems.FRAGILE_CLOUD.get());*/
            var stack1 = true; /*AccessoriesCapability.get(player).getEquipped(ModItems.MOON_STONE.get());*/

            AttributeInstance gravity = player.getAttribute(Attributes.GRAVITY);
            if (!stack0 && stack1) {
                if (!player.isFallFlying() && !player.onGround() && !player.isInWater()) {
                    assert gravity != null;
                    if (!gravity.hasModifier(SLOW_FALLING.id()) && player.getDeltaMovement().y < -0.3)
                        gravity.addPermanentModifier(SLOW_FALLING);

                    player.resetFallDistance();
                    if (gravity.hasModifier(SLOW_FALLING.id())) {
                        double particleX = player.getX() + (player.getRandom().nextBoolean() ? -1 : 1) * Math.pow(player.getRandom().nextFloat(), 1) * 1;
                        double particleY = player.getY() + player.getRandom().nextFloat() * 1 - 2;
                        double particleZ = player.getZ() + (player.getRandom().nextBoolean() ? -1 : 1) * Math.pow(player.getRandom().nextFloat(), 1) * 1;
                        ((ServerLevel) player.getCommandSenderWorld()).sendParticles(ParticleTypes.CLOUD, particleX, particleY, particleZ, 1, 1D, 1D, 1D, 0.1);
                    }
                } else {
                    assert gravity != null;
                    if (gravity.hasModifier(SLOW_FALLING.id())) {
                        gravity.removeModifier(SLOW_FALLING);
                    }
                }
            } else {
                assert gravity != null;
                if (gravity.hasModifier(SLOW_FALLING.id())) {
                    gravity.removeModifier(SLOW_FALLING);
                }
            }
        }

    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = FragileCloudBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.fragile_cloud_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.fragile_cloud_1").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public boolean isEnable = true;

    }
}