package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NamelessTrinkets.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.MISSING_PAGE.get());
        basicItem(ModItems.REVERSE_CARD.get());
        basicItem(ModItems.EXPERIENCE_BATTERY.get());
        basicItem(ModItems.BROKEN_ANKH.get());
        basicItem(ModItems.EXPERIENCE_MAGNET.get());
        basicItem(ModItems.BROKEN_MAGNET.get());
        basicItem(ModItems.SUPER_MAGNET.get());
        basicItem(ModItems.WHAT_MAGNET.get());
        basicItem(ModItems.CALLUS.get());
        basicItem(ModItems.SPEED_FORCE.get());
        basicItem(ModItems.VAMPIRE_BLOOD.get());
        basicItem(ModItems.LUCKY_ROCK.get());
        basicItem(ModItems.PUFFER_FISH_LIVER.get());
        basicItem(ModItems.RAGE_MIND.get());
        basicItem(ModItems.TICK.get());
        basicItem(ModItems.BLINDFOLD.get());
        basicItem(ModItems.EXPLOSION_PROOF_JACKET.get());
        basicItem(ModItems.CRACKED_CROWN.get());
        basicItem(ModItems.GHAST_EYE.get());
        basicItem(ModItems.WOODEN_STICK.get());
        basicItem(ModItems.BLAZE_NUCLEUS.get());
        basicItem(ModItems.ICE_CUBE.get());
        basicItem(ModItems.SIGIL_OF_BAPHOMET.get());
        basicItem(ModItems.CREEPER_SENSE.get());
        basicItem(ModItems.FERTILIZER.get());
        basicItem(ModItems.GODS_CROWN.get());
        basicItem(ModItems.AMPHIBIOUS_HANDS.get());
        basicItem(ModItems.GILLS.get());
        basicItem(ModItems.MOON_STONE.get());
        basicItem(ModItems.SLEEPING_PILLS.get());
        basicItem(ModItems.ETHEREAL_WINGS.get());
        basicItem(ModItems.SPIDER_LEGS.get());
        basicItem(ModItems.REFORGER.get());
        basicItem(ModItems.ELECTRIC_PADDLE.get());
        basicItem(ModItems.FRACTURED_NULLSTONE.get());
        basicItem(ModItems.POCKET_LIGHTNING_ROD.get());
        basicItem(ModItems.FRAGILE_CLOUD.get());
        basicItem(ModItems.SCARAB_AMULET.get());
        basicItem(ModItems.FATE_EMERALD.get());
        basicItem(ModItems.LIGHT_GLOVES.get());
        basicItem(ModItems.DRAGONS_EYE.get());
        basicItem(ModItems.FOUR_LEAF_CLOVER.get());
        basicItem(ModItems.NELUMBO.get());
        basicItem(ModItems.DARK_NELUMBO.get());
        basicItem(ModItems.MINERS_SOUL.get());
        basicItem(ModItems.TRUE_HEART_OF_THE_SEA.get());
        basicItem(ModItems.TEAR_OF_THE_SEA.get());
        basicItem(ModItems.SHRINKING_VEIL.get());
        basicItem(ModItems.TITANS_MARK.get());
        basicItem(ModItems.WOUNDBEARER.get());
        basicItem(ModItems.DYING_STAR.get());
        basicItem(ModItems.RESONANT_HEART.get());
        basicItem(ModItems.TRINKET_BUNDLE.get());
        basicItem(ModItems.MYSTERIOUS_TRINKET.get());
        basicItem(ModItems.UNKNOWN_FRAGMENT.get());
        basicItem(ModItems.DUBIOUS_DUST.get());
        basicItem(ModItems.GLOWING_DUST.get());
        basicItem(ModItems.ULTIMATE_DUST.get());
    }
}