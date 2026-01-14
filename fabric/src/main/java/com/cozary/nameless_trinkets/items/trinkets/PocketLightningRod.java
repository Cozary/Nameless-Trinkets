package com.cozary.nameless_trinkets.items.trinkets;

import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class PocketLightningRod extends PocketLightningRodBase implements Accessory {

    public PocketLightningRod() {
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
        Stats config = PocketLightningRodBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;


        Level level = reference.entity().level();

        if (!level.isClientSide) {
            boolean flag = level.isRaining();
            if (flag && level.isThundering() && level.random.nextInt(config.thunders) == 0) {
                ChunkPos chunkpos = reference.entity().chunkPosition();
                int i = chunkpos.getMinBlockX();
                int j = chunkpos.getMinBlockZ();
                BlockPos blockpos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, level.getBlockRandomPos(i, 0, j, 15));
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
                assert lightningbolt != null;
                lightningbolt.snapTo(Vec3.atBottomCenterOf(blockpos));
                level.addFreshEntity(lightningbolt);
            }
        }

    }

}