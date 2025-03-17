package com.cozary.nameless_trinkets.events;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.items.trinkets.BlazeNucleus;
import com.cozary.nameless_trinkets.items.trinkets.DarkNelumbo;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NamelessTrinkets.MOD_ID)
public class DarkNelumboEvents {

    @SubscribeEvent
    public static void blazeNucleusImmune(LivingDamageEvent.Pre event) {
        DarkNelumbo.Stats config = DarkNelumbo.INSTANCE.getTrinketConfig();

        if (!config.isEnable)
            return;

        if (!(event.getEntity() instanceof Player player) || player.isSpectator())
            return;

        var accessories = AccessoriesCapability.get(player);

        if (accessories == null) {
            return;
        }
        var stack = accessories.getEquipped(ModItems.DARK_NELUMBO.get());
        if (!stack.isEmpty()) {
            if (config.cancelLavaDamage) {
                if (event.getSource().type().msgId().equals("lava")) {
                    event.setNewDamage(0);
                }
            }
        }
    }
}
