package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import java.util.List;

public class DragonsEye extends TrinketItem<DragonsEye.Stats> {
    public static DragonsEye INSTANCE;

    public DragonsEye() {
        super(new TrinketData(null,null, Stats.class));

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
        Stats config = DragonsEye.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.dragons_eye_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.dragons_eye_1", config.radius).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
                tooltip.add(Component.translatable(ChatFormatting.GRAY + "Suggested By: emu"));

            }
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = DragonsEye.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(reference.entity() instanceof Player player)) {
            return;
        }

        if (config.blindness) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1));
        }

        Level world = player.level();
        List<Mob> entities = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(config.radius));
        Scoreboard scoreboard = player.getScoreboard();

        PlayerTeam playerTeam = scoreboard.getPlayerTeam("dragonsEyeTargets");
        if (playerTeam == null) {
            playerTeam = scoreboard.addPlayerTeam("dragonsEyeTargets");
            playerTeam.setColor(ChatFormatting.LIGHT_PURPLE);
        }

        for (Mob entity : entities) {
            if (entity.shouldDespawnInPeaceful() || entity.getSoundSource() == SoundSource.HOSTILE || entity.isAggressive()) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 30));
                scoreboard.addPlayerToTeam(entity.getStringUUID(), playerTeam);
            }
        }
    }



    public static class Stats extends TrinketsStats {
        public float radius = 20.0F;
        public boolean blindness = true;
        public boolean isEnable = true;

    }

}