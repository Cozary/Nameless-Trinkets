package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
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

import java.util.List;

public class DragonsEye extends DragonsEyeBase implements Accessory {

    public DragonsEye() {
        super();
        AccessoryRegistry.register(this, this);
    }

    @Override
    public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public void onEquipFromUse(ItemStack stack, SlotReference reference) {
        reference.entity().playSound(SoundEvents.ARMOR_EQUIP_ELYTRA.value(), 1.0F, 1.0F);
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = DragonsEyeBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(reference.entity() instanceof Player player)) {
            return;
        }

        if (config.blindness) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1));
        }

        Level world = player.level();
        List<Mob> entities = world.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(config.radius));
        Scoreboard scoreboard = player.level().getScoreboard();

        PlayerTeam playerTeam = scoreboard.getPlayerTeam("dragonsEyeTargets");
        if (playerTeam == null) {
            playerTeam = scoreboard.addPlayerTeam("dragonsEyeTargets");
            playerTeam.setColor(ChatFormatting.LIGHT_PURPLE);
        }

        for (Mob entity : entities) {
            if (entity.isAggressive() || entity.getSoundSource() == SoundSource.HOSTILE || entity.isAggressive()) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 30));
                scoreboard.addPlayerToTeam(entity.getStringUUID(), playerTeam);
            }
        }
    }

}