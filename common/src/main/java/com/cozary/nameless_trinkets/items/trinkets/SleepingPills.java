package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class SleepingPills extends TrinketItem<SleepingPills.Stats> {
    public static SleepingPills INSTANCE;

    public SleepingPills() {
        super(new TrinketData(new Item.Properties().stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "sleeping_pills")))
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
    public void tick(ItemStack stack, SlotReference reference) {
        SleepingPills.Stats config = SleepingPills.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();

        if (!livingEntity.isSpectator()) {

            if (!stack.isEmpty()) {
                if (!livingEntity.hasEffect(MobEffects.NIGHT_VISION) || Objects.requireNonNull(livingEntity.getEffect(MobEffects.NIGHT_VISION)).getDuration() < 600)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, config.nightVisionTime, 0, false, false));

                if (!livingEntity.hasEffect(MobEffects.WEAKNESS) || Objects.requireNonNull(livingEntity.getEffect(MobEffects.WEAKNESS)).getDuration() < 600)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, config.weaknessTime, 0, false, false));

                List<Phantom> list = livingEntity.level().getEntitiesOfClass(Phantom.class, livingEntity.getBoundingBox().inflate(config.phantomRange), EntitySelector.ENTITY_STILL_ALIVE);

                if (!list.isEmpty()) {
                    for (Phantom phantomEntity : list) {
                        if (!phantomEntity.level().isClientSide) {

                            Vec3 vector3d = phantomEntity.getDeltaMovement();
                            ((ServerLevel) phantomEntity.getCommandSenderWorld()).sendParticles(ParticleTypes.ASH, phantomEntity.getX(), phantomEntity.getY(), phantomEntity.getZ(), 250, vector3d.x, 0.3D, vector3d.z, 5);
                        }

                        phantomEntity.discard();
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = SleepingPills.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.sleeping_pills_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.sleeping_pills_1", config.phantomRange).withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.sleeping_pills_2").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.sleeping_pills_3").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.sleeping_pills_4").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.add(Component.translatable(ChatFormatting.GRAY + "Suggested By: JayOnline_"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public int nightVisionTime = 1200;
        public int weaknessTime = 1200;
        public double phantomRange = 2.0F;
        public boolean bedDisabled = true;
        public boolean isEnable = true;
    }

}