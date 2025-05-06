package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class RageMind extends RageMindBase implements ICurioItem {

    @SuppressWarnings("unchecked")
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Stats config = RageMindBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(slotContext.entity() instanceof Player player))
            return;

        if (player.level().isClientSide)
            return;

        if (stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get()) != null) {

            String entityString = stack.get(ModDataComponents.RAGE_MIND_REVENGE_TARGET.get());
            ResourceLocation resourceLocation = ResourceLocation.parse(entityString);

            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);

            Entity entity = entityType.create(player.level());

            if (entity == null) {
                return;
            }

            Class<? extends LivingEntity> classEntity = (Class<? extends LivingEntity>) entity.getClass();

            AABB targetBox = new AABB(player.position(), player.position()).inflate(config.range);

            List<LivingEntity> foundTarget = (List<LivingEntity>) player.level().getEntitiesOfClass(classEntity, targetBox);

            Scoreboard scoreboard = player.getScoreboard();

            PlayerTeam playerTeam = scoreboard.getPlayerTeam("rageMindRevengeTargets");
            if (playerTeam == null) {
                playerTeam = scoreboard.addPlayerTeam("rageMindRevengeTargets");
                playerTeam.setColor(ChatFormatting.DARK_RED);
            }

            if (!foundTarget.isEmpty()) {
                for (Entity revengeTarget : foundTarget) {
                    if (revengeTarget instanceof LivingEntity livingRevengeTarget) {

                        MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.GLOWING, 20, 20);

                        scoreboard.addPlayerToTeam(entity.getStringUUID(), playerTeam);

                        livingRevengeTarget.addEffect(effectinstance);
                    }
                }
            }
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