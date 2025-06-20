package com.cozary.nameless_trinkets.items.trinkets;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.utils.EntityUtils;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class MoonStone extends MoonStoneBase implements Accessory {

    public MoonStone() {
        super();
        AccessoriesAPI.registerAccessory(this, this);
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
    public void tick(ItemStack stack, SlotReference reference) {
        Stats config = MoonStoneBase.INSTANCE.getTrinketConfig();
        if (!config.isEnable)
            return;

        LivingEntity livingEntity = reference.entity();
        Level world = livingEntity.getCommandSenderWorld();

        if (world.isClientSide() || livingEntity.tickCount % 20 != 0)
            return;

        AttributeInstance attribSpeed = livingEntity.getAttribute(Attributes.GRAVITY);
        AttributeModifier speedModifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "moon_stone_gravity"),
                config.gravityValue, AttributeModifier.Operation.ADD_VALUE);

        assert attribSpeed != null;
        EntityUtils.applyAttributeModifier(attribSpeed, speedModifier);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        EntityUtils.removeAttributeModifier(Objects.requireNonNull(reference.entity().getAttribute(Attributes.GRAVITY)),
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "moon_stone_gravity"),
                        trinketConfig.gravityValue, AttributeModifier.Operation.ADD_VALUE));
    }


}