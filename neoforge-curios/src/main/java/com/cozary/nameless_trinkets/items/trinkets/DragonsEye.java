package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.stream.Collectors;

public class DragonsEye extends DragonsEyeBase implements ICurioItem {

    private String getTeamName(Player player) {
        return "nt_dragon_" + player.getStringUUID().substring(0, 8);
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
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player) || player.level().isClientSide()) {
            return;
        }

        Scoreboard scoreboard = player.level().getScoreboard();
        String teamName = getTeamName(player);
        PlayerTeam playerTeam = scoreboard.getPlayerTeam(teamName);

        if (playerTeam != null) {
            scoreboard.removePlayerTeam(playerTeam);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = DragonsEyeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(slotContext.entity() instanceof Player player) || player.level().isClientSide()) {
            return;
        }

        if (config.blindness) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1));
        }

        Scoreboard scoreboard = player.level().getScoreboard();
        String teamName = getTeamName(player);
        PlayerTeam playerTeam = scoreboard.getPlayerTeam(teamName);

        Level world = player.level();
        List<Mob> entities = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(config.radius));
        List<Mob> validTargets = entities.stream()
                .filter(entity -> entity.getSoundSource() == SoundSource.HOSTILE || entity.isAggressive())
                .collect(Collectors.toList());

        if (validTargets.isEmpty()) {
            if (playerTeam != null) {
                scoreboard.removePlayerTeam(playerTeam);
            }
            return;
        }

        if (playerTeam == null) {
            playerTeam = scoreboard.addPlayerTeam(teamName);
            playerTeam.setColor(ChatFormatting.LIGHT_PURPLE);
        }

        List<String> validTargetUUIDs = validTargets.stream().map(Entity::getStringUUID).toList();
        List<String> currentMembers = List.copyOf(playerTeam.getPlayers());

        for (String memberUUID : currentMembers) {
            if (!validTargetUUIDs.contains(memberUUID)) {
                scoreboard.removePlayerFromTeam(memberUUID, playerTeam);
            }
        }

        for (Mob target : validTargets) {
            if (!currentMembers.contains(target.getStringUUID())) {
                scoreboard.addPlayerToTeam(target.getStringUUID(), playerTeam);
            }
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));
        }
    }

}
