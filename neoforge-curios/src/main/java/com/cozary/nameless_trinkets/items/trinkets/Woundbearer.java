package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModDataComponents;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Objects;

public class Woundbearer extends WoundbearerBase implements ICurioItem {

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
        Stats config = WoundbearerBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide())
            return;

        float damageIncrement = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue();

        if (damageIncrement > 0) {

            AttributeInstance attributeDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeModifier damageModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                    damageIncrement, AttributeModifier.Operation.ADD_VALUE);

            assert attributeDamage != null;
            EntityUtils.applyAttributeModifier(attributeDamage, damageModifier);
        }

    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {

        float damageIncrement = stack.getOrDefault(ModDataComponents.WOUNDBEARER_DAMAGE.get(), 0).floatValue();
        if (damageIncrement > 0) {
            EntityUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE)),
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "woundbearer_attack_damage"),
                            damageIncrement, AttributeModifier.Operation.ADD_VALUE));
        }
    }


}