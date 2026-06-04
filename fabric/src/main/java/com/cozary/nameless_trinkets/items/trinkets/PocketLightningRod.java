package com.cozary.nameless_trinkets.items.trinkets;
import net.minecraft.world.entity.LivingEntity;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.SlotReference;

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

public class PocketLightningRod extends PocketLightningRodBase implements Trinket {

    public PocketLightningRod() {
        super();
        }

    

    

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        Stats config = PocketLightningRodBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;


        Level level = entity.level();

        if (!level.isClientSide()) {
            boolean flag = level.isRaining();
            if (flag && level.isThundering() && level.random.nextInt(config.thunders) == 0) {
                ChunkPos chunkpos = entity.chunkPosition();
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