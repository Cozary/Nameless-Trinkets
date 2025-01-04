package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import java.util.List;

public class RageMind extends TrinketItem<RageMind.Stats> {
    public static RageMind INSTANCE;

    public RageMind() {
        super(new TrinketData(null, null, Stats.class));

        INSTANCE = this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = RageMind.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(reference.entity() instanceof Player player))
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

            if (!foundTarget.isEmpty()) {
                for (Entity revengeTarget : foundTarget) {
                    if (revengeTarget instanceof LivingEntity) {
                        LivingEntity livingRevengeTarget = (LivingEntity) revengeTarget;

                        MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.GLOWING, 20, 20);
                        Scoreboard scoreboard = player.getScoreboard();

                        if (!scoreboard.getTeamNames().contains("rageMindRevengeTargets"))
                            scoreboard.addPlayerTeam("rageMindRevengeTargets");

                        PlayerTeam playerteam = player.level().getScoreboard().getPlayerTeam("rageMindRevengeTargets");

                        if (playerteam == null)
                            return;

                        scoreboard.addPlayerToTeam(livingRevengeTarget.getStringUUID(), playerteam);
                        playerteam.setColor(ChatFormatting.RED);
                        livingRevengeTarget.addEffect(effectinstance);
                    }
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Stats config = RageMind.INSTANCE.getTrinketConfig();
        if (!config.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.rage_mind_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.rage_mind_1").withStyle(ChatFormatting.GOLD));
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.rage_mind_2", config.damageMultiplier).withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float damageMultiplier = 1.5F;
        public float range = 50;
        public boolean isEnable = true;
    }

}