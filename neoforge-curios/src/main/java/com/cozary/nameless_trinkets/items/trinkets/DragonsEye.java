package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class DragonsEye extends DragonsEyeBase implements ICurioItem {

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
        Stats config = DragonsEyeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(slotContext.entity() instanceof Player player)) {
            return;
        }

        if (config.blindness) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1));
        }

        Scoreboard scoreboard = player.getScoreboard();
        PlayerTeam playerTeam = scoreboard.getPlayerTeam("dragonsEyeTargets");

        // Clear existing targets from the team to avoid bloat
        if (playerTeam != null) {
            for (String member : List.copyOf(playerTeam.getPlayers())) {
                scoreboard.removePlayerFromTeam(member, playerTeam);
            }
        }

        Level world = player.level();
        List<Mob> entities = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(config.radius));

        if (!entities.isEmpty()) {
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
    }

}
