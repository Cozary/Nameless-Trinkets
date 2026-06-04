package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

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

import java.util.Objects;

public class MoonStone extends MoonStoneBase implements Trinket {

    public MoonStone() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = MoonStoneBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
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
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(entity.getAttribute(Attributes.GRAVITY)),
                new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "moon_stone_gravity"),
                        trinketConfig.gravityValue, AttributeModifier.Operation.ADD_VALUE));
    }


}