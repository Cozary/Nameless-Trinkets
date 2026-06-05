package com.cozary.nameless_trinkets.util;

import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrinketUtils {

    public static List<SlotEntryReference> getEquippedTrinket(Player player, Item item) {
        if (player == null) {
            return Collections.emptyList();
        }

        var attachment = TrinketsApi.getAttachment(player);

        List<SlotEntryReference> list = new ArrayList<>();
        for (var tuple : attachment.getEquipped(stack -> stack.is(item))) {
            list.add(new SlotEntryReference(tuple.getB()));
        }
        return list;
    }

    public record SlotEntryReference(ItemStack stack) {
    }
}
