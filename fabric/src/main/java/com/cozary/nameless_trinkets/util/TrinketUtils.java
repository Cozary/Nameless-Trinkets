package com.cozary.nameless_trinkets.util;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class TrinketUtils {

    public record SlotEntryReference(ItemStack stack) {}

    public static List<SlotEntryReference> getEquippedTrinket(Player player, Item item) {
        if (player == null) {
            return Collections.emptyList();
        }

        var componentOpt = TrinketsApi.getTrinketComponent(player);
        if (componentOpt.isEmpty()) {
            return Collections.emptyList();
        }

        var component = componentOpt.get();
        var equipped = component.getEquipped(stack -> stack.is(item));

        List<SlotEntryReference> list = new ArrayList<>();
        for (var tuple : equipped) {
            list.add(new SlotEntryReference(tuple.getB()));
        }
        return list;
    }
}
