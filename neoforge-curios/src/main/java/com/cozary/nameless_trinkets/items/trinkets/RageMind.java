package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class RageMind extends RageMindBase implements ICurioItem {

    private String getTeamName(Player player) {
        return "nt_rage_" + player.getStringUUID().substring(0, 8);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = RageMindBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable || !(slotContext.entity() instanceof Player player) || player.level().isClientSide()) {
            return;
        }

        Scoreboard scoreboard = player.level().getScoreboard();
        String teamName = getTeamName(player);
        PlayerTeam playerTeam = scoreboard.getPlayerTeam(teamName);

        List<? extends LivingEntity> foundTargets = new ArrayList<>();
        String revengeTarget = stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());

        if (revengeTarget != null) {
            Identifier resourceLocation = Identifier.parse(revengeTarget);
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation).get().value();
            Entity dummyEntity = entityType.create(player.level(), EntitySpawnReason.SPAWN_ITEM_USE);

            if (dummyEntity instanceof LivingEntity) {
                Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) dummyEntity.getClass();
                AABB targetBox = new AABB(player.position(), player.position()).inflate(config.range);
                foundTargets = player.level().getEntitiesOfClass(classEntity, targetBox);
            }
        }

        if (foundTargets.isEmpty()) {
            if (playerTeam != null) {
                scoreboard.removePlayerTeam(playerTeam);
            }
            return;
        }

        if (playerTeam == null) {
            playerTeam = scoreboard.addPlayerTeam(teamName);
            playerTeam.setColor(ChatFormatting.DARK_RED);
        }

        List<String> validTargetUUIDs = foundTargets.stream().map(Entity::getStringUUID).toList();
        List<String> currentMembers = List.copyOf(playerTeam.getPlayers());

        for (String memberUUID : currentMembers) {
            if (!validTargetUUIDs.contains(memberUUID)) {
                scoreboard.removePlayerFromTeam(memberUUID, playerTeam);
            }
        }

        for (LivingEntity target : foundTargets) {
            if (!currentMembers.contains(target.getStringUUID())) {
                scoreboard.addPlayerToTeam(target.getStringUUID(), playerTeam);
            }
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));
        }
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
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        slotContext.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

}
