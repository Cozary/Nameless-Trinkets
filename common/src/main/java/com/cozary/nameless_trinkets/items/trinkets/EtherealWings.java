package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketData;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketItem;
import com.cozary.nameless_trinkets.items.subTrinket.TrinketsStats;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EtherealWings extends TrinketItem<EtherealWings.Stats> {
    public static EtherealWings INSTANCE;

    public EtherealWings() {
        super(new TrinketData(new Item.Properties().stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "amphibious_hands")))
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
    public void onEquip(ItemStack stack, SlotReference reference) {
        Stats config = EtherealWings.INSTANCE.getTrinketConfig();
        if (!config.isEnable || !(reference.entity() instanceof Player player) || player.isCreative() || player.isSpectator()) {
            return;
        }

        player.getAbilities().mayfly = true;
        player.getAbilities().setFlyingSpeed(config.flyingSpeed);
        player.onUpdateAbilities();
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        if (!(reference.entity() instanceof Player player)) {
            return;
        }

        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
        }

        player.getAbilities().setFlyingSpeed(0.05F);
        player.onUpdateAbilities();
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (!trinketConfig.isEnable) {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.isDisabled").withStyle(ChatFormatting.RED));
        } else {
            tooltip.add(Component.translatable("tooltip.nameless_trinkets.ethereal_wings_lore").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.ethereal_wings_1").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.nameless_trinkets.hold_shift"));
            }
        }
    }

    public static class Stats extends TrinketsStats {
        public float flyingSpeed = 0.01F;
        public boolean isEnable = true;

    }

}