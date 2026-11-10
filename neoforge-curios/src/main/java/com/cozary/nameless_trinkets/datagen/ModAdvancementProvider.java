package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ModAdvancementProvider extends AdvancementSubProvider {

    public ModAdvancementProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        HolderGetter<Item> itemRegistry = this.output.lookup(Registries.ITEM);

        AdvancementHolder root = Advancement.Builder.advancement()
                .rootDisplay(ModItems.MYSTERIOUS_TRINKET.get(),
                        Component.translatable("advancements.nameless_trinkets.root.title"),
                        Component.translatable("advancements.nameless_trinkets.root.description"),
                        Identifier.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.CHALLENGE, true, true, false)
                .addCriterion("obtain",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().of(itemRegistry, ModTags.NAMELESS_TRINKETS_TAG)))
                .save(this.output, NamelessTrinkets.MOD_ID + ":root");

        addTrinketAdvancement(root, ModItems.TICK.get(), "tick");
        addTrinketAdvancement(root, ModItems.GILLS.get(), "gills");
        addTrinketAdvancement(root, ModItems.CALLUS.get(), "callus");
        addTrinketAdvancement(root, ModItems.NELUMBO.get(), "nelumbo");
        addTrinketAdvancement(root, ModItems.ICE_CUBE.get(), "ice_cube");
        addTrinketAdvancement(root, ModItems.REFORGER.get(), "reforger");
        addTrinketAdvancement(root, ModItems.BLINDFOLD.get(), "blindfold");
        addTrinketAdvancement(root, ModItems.GHAST_EYE.get(), "ghast_eye");
        addTrinketAdvancement(root, ModItems.RAGE_MIND.get(), "rage_mind");
        addTrinketAdvancement(root, ModItems.DYING_STAR.get(), "dying_star");
        addTrinketAdvancement(root, ModItems.FERTILIZER.get(), "fertilizer");
        addTrinketAdvancement(root, ModItems.GODS_CROWN.get(), "gods_crown");
        addTrinketAdvancement(root, ModItems.LUCKY_ROCK.get(), "lucky_rock");
        addTrinketAdvancement(root, ModItems.MOON_STONE.get(), "moon_stone");
        addTrinketAdvancement(root, ModItems.BROKEN_ANKH.get(), "broken_ankh");
        addTrinketAdvancement(root, ModItems.DRAGONS_EYE.get(), "dragons_eye");
        addTrinketAdvancement(root, ModItems.MINERS_SOUL.get(), "miners_soul");
        addTrinketAdvancement(root, ModItems.SPEED_FORCE.get(), "speed_force");
        addTrinketAdvancement(root, ModItems.TITANS_MARK.get(), "titans_mark");
        addTrinketAdvancement(root, ModItems.WHAT_MAGNET.get(), "what_magnet");
        addTrinketAdvancement(root, ModItems.WOUNDBEARER.get(), "woundbearer");
        addTrinketAdvancement(root, ModItems.DARK_NELUMBO.get(), "dark_nelumbo");
        addTrinketAdvancement(root, ModItems.FATE_EMERALD.get(), "fate_emerald");
        addTrinketAdvancement(root, ModItems.LIGHT_GLOVES.get(), "light_gloves");
        addTrinketAdvancement(root, ModItems.MISSING_PAGE.get(), "missing_page");
        addTrinketAdvancement(root, ModItems.REVERSE_CARD.get(), "reverse_card");
        addTrinketAdvancement(root, ModItems.SUPER_MAGNET.get(), "super_magnet");
        addTrinketAdvancement(root, ModItems.WOODEN_STICK.get(), "wooden_stick");
        addTrinketAdvancement(root, ModItems.BLAZE_NUCLEUS.get(), "blaze_nucleus");
        addTrinketAdvancement(root, ModItems.BROKEN_MAGNET.get(), "broken_magnet");
        addTrinketAdvancement(root, ModItems.CRACKED_CROWN.get(), "cracked_crown");
        addTrinketAdvancement(root, ModItems.CREEPER_SENSE.get(), "creeper_sense");
        addTrinketAdvancement(root, ModItems.FRAGILE_CLOUD.get(), "fragile_cloud");
        addTrinketAdvancement(root, ModItems.SCARAB_AMULET.get(), "scarab_amulet");
        addTrinketAdvancement(root, ModItems.VAMPIRE_BLOOD.get(), "vampire_blood");
        addTrinketAdvancement(root, ModItems.ETHEREAL_WINGS.get(), "ethereal_wings");
        addTrinketAdvancement(root, ModItems.RESONANT_HEART.get(), "resonant_heart");
        addTrinketAdvancement(root, ModItems.SHRINKING_VEIL.get(), "shrinking_veil");
        addTrinketAdvancement(root, ModItems.SLEEPING_PILLS.get(), "sleeping_pills");
        addTrinketAdvancement(root, ModItems.ELECTRIC_PADDLE.get(), "electric_paddle");
        addTrinketAdvancement(root, ModItems.TEAR_OF_THE_SEA.get(), "tear_of_the_sea");
        addTrinketAdvancement(root, ModItems.AMPHIBIOUS_HANDS.get(), "amphibious_hands");
        addTrinketAdvancement(root, ModItems.FOUR_LEAF_CLOVER.get(), "four_leaf_clover");
        addTrinketAdvancement(root, ModItems.UNKNOWN_FRAGMENT.get(), "unknown_fragment");
        addTrinketAdvancement(root, ModItems.EXPERIENCE_MAGNET.get(), "experience_magnet");
        addTrinketAdvancement(root, ModItems.PUFFER_FISH_LIVER.get(), "puffer_fish_liver");
        addTrinketAdvancement(root, ModItems.SIGIL_OF_BAPHOMET.get(), "sigil_of_baphomet");
        addTrinketAdvancement(root, ModItems.EXPERIENCE_BATTERY.get(), "experience_battery");
        addTrinketAdvancement(root, ModItems.FRACTURED_NULLSTONE.get(), "fractured_nullstone");
        addTrinketAdvancement(root, ModItems.POCKET_LIGHTNING_ROD.get(), "pocket_lightning_rod");
        addTrinketAdvancement(root, ModItems.TRUE_HEART_OF_THE_SEA.get(), "true_heart_of_the_sea");
        addTrinketAdvancement(root, ModItems.EXPLOSION_PROOF_JACKET.get(), "explosion_proof_jacket");
        addTrinketAdvancement(root, ModItems.SPIDER_LEGS.get(), "spider_legs");
        addTrinketAdvancement(root, ModItems.LUNAR_CREST.get(), "lunar_crest");
        addTrinketAdvancement(root, ModItems.ECLIPSE_ASHES.get(), "eclipse_ashes");
        AdvancementHolder shatteredHourglass = addTrinketAdvancement(root,
                ModItems.SHATTERED_HOURGLASS.get(), "shattered_hourglass");
        addAwakenedHourglassAdvancement(shatteredHourglass, ModItems.AWAKENED_HOURGLASS_TIME_STOP.get(),
                "awakened_hourglass_time_stop");
        addAwakenedHourglassAdvancement(shatteredHourglass,
                ModItems.AWAKENED_HOURGLASS_ACCELERATION.get(), "awakened_hourglass_acceleration");
        addAwakenedHourglassAdvancement(shatteredHourglass, ModItems.AWAKENED_HOURGLASS_DILATION.get(),
                "awakened_hourglass_dilation");
        addTrinketAdvancement(root, ModItems.PARASITE_HIVE.get(), "parasite_hive");
    }

    private AdvancementHolder addTrinketAdvancement(AdvancementHolder parent, Item item, String name) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(
                        item,
                        Component.translatable("advancements.nameless_trinkets." + name + ".title"),
                        Component.translatable("advancements.nameless_trinkets." + name + ".description"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false)
                .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(item))
                .save(this.output, NamelessTrinkets.MOD_ID + ":" + name);
    }

    private void addAwakenedHourglassAdvancement(AdvancementHolder parent, Item item, String name) {
        Advancement.Builder.advancement()
                .parent(parent)
                .display(
                        item,
                        Component.translatable("advancements.nameless_trinkets." + name + ".title"),
                        Component.translatable("advancements.nameless_trinkets." + name + ".description"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false)
                .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(item))
                .save(this.output, NamelessTrinkets.MOD_ID + ":" + name);
    }
}
