package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.resources.Identifier;
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

public class MoonStone extends MoonStoneBase implements ICurioItem {

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
        Stats config = MoonStoneBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = slotContext.entity();
        Level world = livingEntity.level();

        if (world.isClientSide() || livingEntity.tickCount % 20 != 0)
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.GRAVITY);
        AttributeModifier speedModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "moon_stone_gravity"),
                config.gravityValue, AttributeModifier.Operation.ADD_VALUE);

        assert attribSpeed != null;
        CommonUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack pstack, ItemStack stack) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(slotContext.entity().getAttribute(Attributes.GRAVITY)),
                new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "moon_stone_gravity"),
                        trinketConfig.gravityValue, AttributeModifier.Operation.ADD_VALUE));
    }


}
