package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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

import java.util.List;

public class RageMind extends RageMindBase implements Accessory {

    public RageMind() {
        super();
        AccessoriesAPI.registerAccessory(this, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = RageMindBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof Player player))
            return;

        if (player.level().isClientSide)
            return;

        Scoreboard scoreboard = player.getScoreboard();
        PlayerTeam playerTeam = scoreboard.getPlayerTeam("rageMindRevengeTargets");

        // Clear existing targets from the team to avoid bloat
        if (playerTeam != null) {
            for (String member : List.copyOf(playerTeam.getPlayers())) {
                scoreboard.removePlayerFromTeam(member, playerTeam);
            }
        }

        if (stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

            String entityString = stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());
            ResourceLocation resourceLocation = ResourceLocation.parse(entityString);

            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation).get().value();

            Entity entity = entityType.create(player.level(), EntitySpawnReason.SPAWN_ITEM_USE);

            if (entity == null) {
                return;
            }

            Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) entity.getClass();

            AABB targetBox = new AABB(player.position(), player.position()).inflate(config.range);

            List<LivingEntity> foundTarget = (List<LivingEntity>) player.level().getEntitiesOfClass(classEntity, targetBox);

            if (!foundTarget.isEmpty()) {
                if (playerTeam == null) {
                    playerTeam = scoreboard.addPlayerTeam("rageMindRevengeTargets");
                    playerTeam.setColor(ChatFormatting.DARK_RED);
                }

                for (LivingEntity revengeTarget : foundTarget) {
                    MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.GLOWING, 20, 20);

                    scoreboard.addPlayerToTeam(revengeTarget.getStringUUID(), playerTeam);

                    revengeTarget.addEffect(effectinstance);
                }
            }
        }
    }


    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

}
