package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import com.cozary.nameless_trinkets.utils.ConfigurationHandler;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class SpeedForce extends TrinketItem<SpeedForce.Stats> {
    public static SpeedForce INSTANCE;

    public SpeedForce() {
        super(new TrinketData(new Item.Properties().stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force")))
                , null,
                Stats.class));
        INSTANCE = this;
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = SpeedForce.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.speed_force_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.speed_force_1", config.speedMultiplier + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.speed_force_2").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = SpeedForce.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();


        if (!livingEntity.level().isClientSide && !stack.isEmpty() && ConfigurationHandler.GENERAL.speedForceParticles.get()) {

            Vec3 vector3d = livingEntity.getDeltaMovement();
            ((ServerLevel) livingEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.CLOUD, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, vector3d.x * -4.0D, 0.3D, vector3d.z * -4.0D, 0.1);
        }

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force_movement_speed"),
                config.speedMultiplier / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        EntityUtils.removeAttributeModifier(Objects.requireNonNull(reference.entity().getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "speed_force_movement_speed"),
                        trinketConfig.speedMultiplier / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }


    public static class Stats extends TrinketsStats {
        public double speedMultiplier = 200.0;
        public boolean isEnable = true;

    }

}