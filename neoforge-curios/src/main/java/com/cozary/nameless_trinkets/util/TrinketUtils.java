package com.cozary.nameless_trinkets.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class TrinketUtils {

    public static ItemStack getEquippedTrinket(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player).map(handler ->
                handler.findCurios(item).stream()
                        .findFirst()
                        .map(SlotResult::stack)
                        .orElse(ItemStack.EMPTY)
        ).orElse(ItemStack.EMPTY);
    }

}
