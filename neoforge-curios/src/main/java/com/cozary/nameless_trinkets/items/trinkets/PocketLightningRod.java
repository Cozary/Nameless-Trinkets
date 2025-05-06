package com.cozary.nameless_trinkets.items.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class PocketLightningRod extends PocketLightningRodBase implements ICurioItem {

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
        Stats config = PocketLightningRodBase.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;


        Level level = slotContext.entity().level();

        if (!level.isClientSide) {
            boolean flag = level.isRaining();
            if (flag && level.isThundering() && level.random.nextInt(config.thunders) == 0) {
                ChunkPos chunkpos = slotContext.entity().chunkPosition();
                int i = chunkpos.getMinBlockX();
                int j = chunkpos.getMinBlockZ();
                BlockPos blockpos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, level.getBlockRandomPos(i, 0, j, 15));
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
                assert lightningbolt != null;
                lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
                level.addFreshEntity(lightningbolt);
            }
        }

    }

}