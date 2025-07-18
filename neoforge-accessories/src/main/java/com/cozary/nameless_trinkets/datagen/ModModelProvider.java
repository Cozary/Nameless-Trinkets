package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, NamelessTrinkets.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.MISSING_PAGE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.REVERSE_CARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EXPERIENCE_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BROKEN_ANKH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EXPERIENCE_MAGNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BROKEN_MAGNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SUPER_MAGNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WHAT_MAGNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CALLUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPEED_FORCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.VAMPIRE_BLOOD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LUCKY_ROCK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PUFFER_FISH_LIVER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAGE_MIND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLINDFOLD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EXPLOSION_PROOF_JACKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRACKED_CROWN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GHAST_EYE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WOODEN_STICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLAZE_NUCLEUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ICE_CUBE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SIGIL_OF_BAPHOMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CREEPER_SENSE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FERTILIZER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GODS_CROWN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AMPHIBIOUS_HANDS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GILLS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MOON_STONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SLEEPING_PILLS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ETHEREAL_WINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SPIDER_LEGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.REFORGER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ELECTRIC_PADDLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FRACTURED_NULLSTONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.POCKET_LIGHTNING_ROD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FRAGILE_CLOUD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SCARAB_AMULET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FATE_EMERALD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIGHT_GLOVES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DRAGONS_EYE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FOUR_LEAF_CLOVER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NELUMBO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DARK_NELUMBO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MINERS_SOUL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TRUE_HEART_OF_THE_SEA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TEAR_OF_THE_SEA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SHRINKING_VEIL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TITANS_MARK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WOUNDBEARER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DYING_STAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RESONANT_HEART.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TRINKET_BUNDLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MYSTERIOUS_TRINKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNKNOWN_FRAGMENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DUBIOUS_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GLOWING_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ULTIMATE_DUST.get(), ModelTemplates.FLAT_ITEM);
    }
}