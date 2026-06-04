package com.cozary.nameless_trinkets.items.trinkets;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.CommonUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ScarabAmulet extends ScarabAmuletBase implements Trinket {

    public ScarabAmulet() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = ScarabAmuletBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = entity;
        Level world = livingEntity.level();

        if (world.isClientSide())
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedModifier = new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "scarab_amulet_movement_speed"),
                config.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        assert attribSpeed != null;
        if (livingEntity.level().getBlockState(getBlockPosBelowThatAffectsMyMovement(livingEntity)).is(BlockTags.SAND)) {
            CommonUtils.applyAttributeModifier(attribSpeed, speedModifier);
        } else {
            CommonUtils.removeAttributeModifier(Objects.requireNonNull(entity.getAttribute(Attributes.MOVEMENT_SPEED)),
                    new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "scarab_amulet_movement_speed"),
                            config.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        CommonUtils.removeAttributeModifier(Objects.requireNonNull(entity.getAttribute(Attributes.MOVEMENT_SPEED)),
                new AttributeModifier(Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "scarab_amulet_movement_speed"),
                        trinketConfig.speedMultiplierPercentage / 100, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

}