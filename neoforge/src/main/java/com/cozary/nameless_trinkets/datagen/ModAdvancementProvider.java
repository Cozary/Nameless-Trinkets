package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new AdvancementGenerator()));
    }



    private static final class AdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            generateRoot(saver, existingFileHelper, ModItems.MYSTERIOUS_TRINKET.get());

            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.AMPHIBIOUS_HANDS.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.BLAZE_NUCLEUS.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.BLINDFOLD.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.BROKEN_ANKH.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.BROKEN_MAGNET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.CALLUS.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.CRACKED_CROWN.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.CREEPER_SENSE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.DARK_NELUMBO.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.DRAGONS_EYE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.DYING_STAR.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.ELECTRIC_PADDLE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.ETHEREAL_WINGS.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.EXPERIENCE_BATTERY.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.EXPERIENCE_MAGNET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.EXPLOSION_PROOF_JACKET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.FATE_EMERALD.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.FERTILIZER.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.FOUR_LEAF_CLOVER.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.FRACTURED_NULLSTONE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.FRAGILE_CLOUD.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.GHAST_EYE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.GILLS.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.GODS_CROWN.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.ICE_CUBE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.LIGHT_GLOVES.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.LUCKY_ROCK.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.MINERS_SOUL.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.MISSING_PAGE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.MOON_STONE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.NELUMBO.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.POCKET_LIGHTNING_ROD.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.PUFFER_FISH_LIVER.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.RAGE_MIND.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.REFORGER.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.RESONANT_HEART.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.REVERSE_CARD.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.SCARAB_AMULET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.SHRINKING_VEIL.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.SIGIL_OF_BAPHOMET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.SLEEPING_PILLS.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.SPEED_FORCE.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.SUPER_MAGNET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.TEAR_OF_THE_SEA.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.TICK.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.TITANS_MARK.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.TRUE_HEART_OF_THE_SEA.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.UNKNOWN_FRAGMENT.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.VAMPIRE_BLOOD.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.WHAT_MAGNET.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.WOODEN_STICK.get());
            generateSimpleItemAdvancement(saver, existingFileHelper, ModItems.WOUNDBEARER.get());
        }
    }

    protected static String getItemName(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

    public static void generateSimpleItemAdvancement(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, Item item) {
        Advancement.Builder builder = Advancement.Builder.advancement();


        builder.parent(AdvancementSubProvider.createPlaceholder("nameless_trinkets:root"));

        builder.display(
                new ItemStack(item),
                Component.translatable("advancements.nameless_trinkets."  + getItemName(item) + ".title"),
                Component.translatable("advancements.nameless_trinkets."  + getItemName(item) + ".description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
        );

        builder.addCriterion(getItemName(item), InventoryChangeTrigger.TriggerInstance.hasItems(item));

        builder.save(saver, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, getItemName(item)), existingFileHelper);
    }

    public static void generateRoot(Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, Item item) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        builder.display(
                new ItemStack(item),
                Component.translatable("advancements.nameless_trinkets.root.title"),
                Component.translatable("advancements.nameless_trinkets.root.description"),
                ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                AdvancementType.CHALLENGE,
                true,
                true,
                false
        );

        builder.addCriterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(ModTags.NAMELESS_TRINKETS_TAG).build()
        ));

        builder.save(saver, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "root"), existingFileHelper);
    }


}