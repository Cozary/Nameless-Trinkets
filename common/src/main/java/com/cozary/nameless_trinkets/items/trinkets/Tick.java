package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class Tick extends TrinketItem<Tick.Stats> {
    public static Tick INSTANCE;

    public Tick() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;
    }

    public static boolean isValidTarget(LivingEntity ent) {
        return (ent.getType() != EntityType.PLAYER) && (!ent.isInvulnerable());
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
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        Player player = (Player) livingEntity;
        return livingEntity.isOnFire() || player.getAbilities().instabuild;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {

        Tick.Stats config = Tick.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof ServerPlayer player) || player.isSpectator())
            return;


        if (!stack.isEmpty()) {

            AABB targetBox = new AABB(player.position(), player.position()).inflate(config.rangeToActivate);
            List<LivingEntity> foundTarget =
                    player.level().getEntitiesOfClass(LivingEntity.class, targetBox, Tick::isValidTarget);

            if (!foundTarget.isEmpty()) {
                for (LivingEntity livingEntity : foundTarget) {

                    if ((livingEntity.getMaxHealth() > 50 && livingEntity.getHealth() > livingEntity.getMaxHealth() / 2) && player.getHealth() >= 5) {

                        livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.entityDamagePercentage / 100));
                        player.hurt(livingEntity.damageSources().generic(), player.getMaxHealth() * (config.playerDamagePercentage / 100));

                    } else if ((livingEntity.getMaxHealth() > 50 && livingEntity.getHealth() > livingEntity.getMaxHealth() / 2) && player.getFoodData().getFoodLevel() >= 5) {

                        livingEntity.hurt(livingEntity.damageSources().generic(), livingEntity.getMaxHealth() * (config.entityDamagePercentage / 100));
                        player.causeFoodExhaustion(player.getFoodData().getFoodLevel() * (config.playerHungerPercentage / 100));
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = Tick.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.tick_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.tick_1", config.entityDamagePercentage + "%").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.tick_2").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.tick_3").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.tick_4").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float rangeToActivate = 25.0F;
        public float entityDamagePercentage = 1.0F;
        public float playerDamagePercentage = 5.0F;
        public float playerHungerPercentage = 10.0F;
        public boolean isEnable = true;

    }

}