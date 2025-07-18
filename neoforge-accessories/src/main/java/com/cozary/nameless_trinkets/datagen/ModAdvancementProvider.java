package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, List.of(new AdvancementGenerator()));
    }

    protected static String getItemName(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

    public static void generateSimpleItemAdvancement(Consumer<AdvancementHolder> saver, Item item) {
        Advancement.Builder builder = Advancement.Builder.advancement();


        builder.parent(AdvancementSubProvider.createPlaceholder("nameless_trinkets:root"));

        builder.display(
                new ItemStack(item),
                Component.translatable("advancements.nameless_trinkets." + getItemName(item) + ".title"),
                Component.translatable("advancements.nameless_trinkets." + getItemName(item) + ".description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
        );

        builder.addCriterion(getItemName(item), InventoryChangeTrigger.TriggerInstance.hasItems(item));

        builder.save(saver, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, getItemName(item)));
    }

    public static void generateRoot(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, Item item) {
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
                ItemPredicate.Builder.item()
                        .of(
                                registries.lookupOrThrow(Registries.ITEM),
                                ModTags.NAMELESS_TRINKETS_TAG
                        )
                        .build()
        ));


        builder.save(saver, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "root"));
    }

    private static final class AdvancementGenerator implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            generateRoot(registries, saver, ModItems.MYSTERIOUS_TRINKET.get());

            generateSimpleItemAdvancement(saver, ModItems.AMPHIBIOUS_HANDS.get());
            generateSimpleItemAdvancement(saver, ModItems.BLAZE_NUCLEUS.get());
            generateSimpleItemAdvancement(saver, ModItems.BLINDFOLD.get());
            generateSimpleItemAdvancement(saver, ModItems.BROKEN_ANKH.get());
            generateSimpleItemAdvancement(saver, ModItems.BROKEN_MAGNET.get());
            generateSimpleItemAdvancement(saver, ModItems.CALLUS.get());
            generateSimpleItemAdvancement(saver, ModItems.CRACKED_CROWN.get());
            generateSimpleItemAdvancement(saver, ModItems.CREEPER_SENSE.get());
            generateSimpleItemAdvancement(saver, ModItems.DARK_NELUMBO.get());
            generateSimpleItemAdvancement(saver, ModItems.DRAGONS_EYE.get());
            generateSimpleItemAdvancement(saver, ModItems.DYING_STAR.get());
            generateSimpleItemAdvancement(saver, ModItems.ELECTRIC_PADDLE.get());
            generateSimpleItemAdvancement(saver, ModItems.ETHEREAL_WINGS.get());
            generateSimpleItemAdvancement(saver, ModItems.EXPERIENCE_BATTERY.get());
            generateSimpleItemAdvancement(saver, ModItems.EXPERIENCE_MAGNET.get());
            generateSimpleItemAdvancement(saver, ModItems.EXPLOSION_PROOF_JACKET.get());
            generateSimpleItemAdvancement(saver, ModItems.FATE_EMERALD.get());
            generateSimpleItemAdvancement(saver, ModItems.FERTILIZER.get());
            generateSimpleItemAdvancement(saver, ModItems.FOUR_LEAF_CLOVER.get());
            generateSimpleItemAdvancement(saver, ModItems.FRACTURED_NULLSTONE.get());
            generateSimpleItemAdvancement(saver, ModItems.FRAGILE_CLOUD.get());
            generateSimpleItemAdvancement(saver, ModItems.GHAST_EYE.get());
            generateSimpleItemAdvancement(saver, ModItems.GILLS.get());
            generateSimpleItemAdvancement(saver, ModItems.GODS_CROWN.get());
            generateSimpleItemAdvancement(saver, ModItems.ICE_CUBE.get());
            generateSimpleItemAdvancement(saver, ModItems.LIGHT_GLOVES.get());
            generateSimpleItemAdvancement(saver, ModItems.LUCKY_ROCK.get());
            generateSimpleItemAdvancement(saver, ModItems.MINERS_SOUL.get());
            generateSimpleItemAdvancement(saver, ModItems.MISSING_PAGE.get());
            generateSimpleItemAdvancement(saver, ModItems.MOON_STONE.get());
            generateSimpleItemAdvancement(saver, ModItems.NELUMBO.get());
            generateSimpleItemAdvancement(saver, ModItems.POCKET_LIGHTNING_ROD.get());
            generateSimpleItemAdvancement(saver, ModItems.PUFFER_FISH_LIVER.get());
            generateSimpleItemAdvancement(saver, ModItems.RAGE_MIND.get());
            generateSimpleItemAdvancement(saver, ModItems.REFORGER.get());
            generateSimpleItemAdvancement(saver, ModItems.RESONANT_HEART.get());
            generateSimpleItemAdvancement(saver, ModItems.REVERSE_CARD.get());
            generateSimpleItemAdvancement(saver, ModItems.SCARAB_AMULET.get());
            generateSimpleItemAdvancement(saver, ModItems.SHRINKING_VEIL.get());
            generateSimpleItemAdvancement(saver, ModItems.SIGIL_OF_BAPHOMET.get());
            generateSimpleItemAdvancement(saver, ModItems.SLEEPING_PILLS.get());
            generateSimpleItemAdvancement(saver, ModItems.SPEED_FORCE.get());
            generateSimpleItemAdvancement(saver, ModItems.SUPER_MAGNET.get());
            generateSimpleItemAdvancement(saver, ModItems.TEAR_OF_THE_SEA.get());
            generateSimpleItemAdvancement(saver, ModItems.TICK.get());
            generateSimpleItemAdvancement(saver, ModItems.TITANS_MARK.get());
            generateSimpleItemAdvancement(saver, ModItems.TRUE_HEART_OF_THE_SEA.get());
            generateSimpleItemAdvancement(saver, ModItems.UNKNOWN_FRAGMENT.get());
            generateSimpleItemAdvancement(saver, ModItems.VAMPIRE_BLOOD.get());
            generateSimpleItemAdvancement(saver, ModItems.WHAT_MAGNET.get());
            generateSimpleItemAdvancement(saver, ModItems.WOODEN_STICK.get());
            generateSimpleItemAdvancement(saver, ModItems.WOUNDBEARER.get());
        }
    }


}