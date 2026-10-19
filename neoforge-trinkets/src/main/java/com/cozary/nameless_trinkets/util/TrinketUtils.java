package com.cozary.nameless_trinkets.util;

import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TrinketUtils {

    public static ItemStack getEquippedTrinket(Player player, Item item) {
        if (player == null) {
            return ItemStack.EMPTY;
        }
        var attachment = TrinketsApi.getAttachment(player);
        for (var slot : attachment.equipped(item, true)) {
            return slot.get();
        }
        return ItemStack.EMPTY;
    }
}
