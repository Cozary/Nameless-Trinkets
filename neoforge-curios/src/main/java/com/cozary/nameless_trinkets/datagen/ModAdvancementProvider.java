package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, List<AdvancementSubProvider> subProviders) {
        super(output, registries, subProviders);
    }

    public static class ModAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            HolderLookup.RegistryLookup<Item> itemRegistry = provider.lookupOrThrow(Registries.ITEM);

            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            ModItems.MYSTERIOUS_TRINKET.get(),
                            Component.translatable("advancements.nameless_trinkets.root.title"),
                            Component.translatable("advancements.nameless_trinkets.root.description"),
                            Identifier.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("obtain", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemRegistry, ModTags.NAMELESS_TRINKETS_TAG)))
                    .save(consumer, NamelessTrinkets.MOD_ID + ":root");

            addTrinketAdvancement(consumer, root, ModItems.TICK.get(), "tick");
            addTrinketAdvancement(consumer, root, ModItems.GILLS.get(), "gills");
            addTrinketAdvancement(consumer, root, ModItems.CALLUS.get(), "callus");
            addTrinketAdvancement(consumer, root, ModItems.NELUMBO.get(), "nelumbo");
            addTrinketAdvancement(consumer, root, ModItems.ICE_CUBE.get(), "ice_cube");
            addTrinketAdvancement(consumer, root, ModItems.REFORGER.get(), "reforger");
            addTrinketAdvancement(consumer, root, ModItems.BLINDFOLD.get(), "blindfold");
            addTrinketAdvancement(consumer, root, ModItems.GHAST_EYE.get(), "ghast_eye");
            addTrinketAdvancement(consumer, root, ModItems.RAGE_MIND.get(), "rage_mind");
            addTrinketAdvancement(consumer, root, ModItems.DYING_STAR.get(), "dying_star");
            addTrinketAdvancement(consumer, root, ModItems.FERTILIZER.get(), "fertilizer");
            addTrinketAdvancement(consumer, root, ModItems.GODS_CROWN.get(), "gods_crown");
            addTrinketAdvancement(consumer, root, ModItems.LUCKY_ROCK.get(), "lucky_rock");
            addTrinketAdvancement(consumer, root, ModItems.MOON_STONE.get(), "moon_stone");
            addTrinketAdvancement(consumer, root, ModItems.BROKEN_ANKH.get(), "broken_ankh");
            addTrinketAdvancement(consumer, root, ModItems.DRAGONS_EYE.get(), "dragons_eye");
            addTrinketAdvancement(consumer, root, ModItems.MINERS_SOUL.get(), "miners_soul");
            addTrinketAdvancement(consumer, root, ModItems.SPEED_FORCE.get(), "speed_force");
            addTrinketAdvancement(consumer, root, ModItems.TITANS_MARK.get(), "titans_mark");
            addTrinketAdvancement(consumer, root, ModItems.WHAT_MAGNET.get(), "what_magnet");
            addTrinketAdvancement(consumer, root, ModItems.WOUNDBEARER.get(), "woundbearer");
            addTrinketAdvancement(consumer, root, ModItems.DARK_NELUMBO.get(), "dark_nelumbo");
            addTrinketAdvancement(consumer, root, ModItems.FATE_EMERALD.get(), "fate_emerald");
            addTrinketAdvancement(consumer, root, ModItems.LIGHT_GLOVES.get(), "light_gloves");
            addTrinketAdvancement(consumer, root, ModItems.MISSING_PAGE.get(), "missing_page");
            addTrinketAdvancement(consumer, root, ModItems.REVERSE_CARD.get(), "reverse_card");
            addTrinketAdvancement(consumer, root, ModItems.SUPER_MAGNET.get(), "super_magnet");
            addTrinketAdvancement(consumer, root, ModItems.WOODEN_STICK.get(), "wooden_stick");
            addTrinketAdvancement(consumer, root, ModItems.BLAZE_NUCLEUS.get(), "blaze_nucleus");
            addTrinketAdvancement(consumer, root, ModItems.BROKEN_MAGNET.get(), "broken_magnet");
            addTrinketAdvancement(consumer, root, ModItems.CRACKED_CROWN.get(), "cracked_crown");
            addTrinketAdvancement(consumer, root, ModItems.CREEPER_SENSE.get(), "creeper_sense");
            addTrinketAdvancement(consumer, root, ModItems.FRAGILE_CLOUD.get(), "fragile_cloud");
            addTrinketAdvancement(consumer, root, ModItems.SCARAB_AMULET.get(), "scarab_amulet");
            addTrinketAdvancement(consumer, root, ModItems.VAMPIRE_BLOOD.get(), "vampire_blood");
            addTrinketAdvancement(consumer, root, ModItems.ETHEREAL_WINGS.get(), "ethereal_wings");
            addTrinketAdvancement(consumer, root, ModItems.RESONANT_HEART.get(), "resonant_heart");
            addTrinketAdvancement(consumer, root, ModItems.SHRINKING_VEIL.get(), "shrinking_veil");
            addTrinketAdvancement(consumer, root, ModItems.SLEEPING_PILLS.get(), "sleeping_pills");
            addTrinketAdvancement(consumer, root, ModItems.ELECTRIC_PADDLE.get(), "electric_paddle");
            addTrinketAdvancement(consumer, root, ModItems.TEAR_OF_THE_SEA.get(), "tear_of_the_sea");
            addTrinketAdvancement(consumer, root, ModItems.AMPHIBIOUS_HANDS.get(), "amphibious_hands");
            addTrinketAdvancement(consumer, root, ModItems.FOUR_LEAF_CLOVER.get(), "four_leaf_clover");
            addTrinketAdvancement(consumer, root, ModItems.UNKNOWN_FRAGMENT.get(), "unknown_fragment");
            addTrinketAdvancement(consumer, root, ModItems.EXPERIENCE_MAGNET.get(), "experience_magnet");
            addTrinketAdvancement(consumer, root, ModItems.PUFFER_FISH_LIVER.get(), "puffer_fish_liver");
            addTrinketAdvancement(consumer, root, ModItems.SIGIL_OF_BAPHOMET.get(), "sigil_of_baphomet");
            addTrinketAdvancement(consumer, root, ModItems.EXPERIENCE_BATTERY.get(), "experience_battery");
            addTrinketAdvancement(consumer, root, ModItems.FRACTURED_NULLSTONE.get(), "fractured_nullstone");
            addTrinketAdvancement(consumer, root, ModItems.POCKET_LIGHTNING_ROD.get(), "pocket_lightning_rod");
            addTrinketAdvancement(consumer, root, ModItems.TRUE_HEART_OF_THE_SEA.get(), "true_heart_of_the_sea");
            addTrinketAdvancement(consumer, root, ModItems.EXPLOSION_PROOF_JACKET.get(), "explosion_proof_jacket");
            addTrinketAdvancement(consumer, root, ModItems.SPIDER_LEGS.get(), "spider_legs");
        }

        private void addTrinketAdvancement(Consumer<AdvancementHolder> consumer, AdvancementHolder parent, Item item, String name) {
            Advancement.Builder.advancement()
                    .parent(parent)
                    .display(
                            item,
                            Component.translatable("advancements.nameless_trinkets." + name + ".title"),
                            Component.translatable("advancements.nameless_trinkets." + name + ".description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(item))
                    .save(consumer, NamelessTrinkets.MOD_ID + ":" + name);
        }
    }
}
