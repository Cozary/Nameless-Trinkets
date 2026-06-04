package com.cozary.nameless_trinkets.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.List;

public class TrinketUtils {

    public static List<SlotEntryReference> getEquippedTrinket(Player player, Item item) {
        if (player == null) {
            return Collections.emptyList();
        }

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return Collections.emptyList();
        }

        return accessories.getEquipped(item);
    }


}
