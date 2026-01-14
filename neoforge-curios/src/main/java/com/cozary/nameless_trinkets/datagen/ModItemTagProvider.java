package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, NamelessTrinkets.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.NAMELESS_TRINKETS_TAG)
                .add(
                        ModItems.MISSING_PAGE.get(),
                        ModItems.REVERSE_CARD.get(),
                        ModItems.EXPERIENCE_BATTERY.get(),
                        ModItems.BROKEN_ANKH.get(),
                        ModItems.EXPERIENCE_MAGNET.get(),
                        ModItems.BROKEN_MAGNET.get(),
                        ModItems.SUPER_MAGNET.get(),
                        ModItems.WHAT_MAGNET.get(),
                        ModItems.CALLUS.get(),
                        ModItems.SPEED_FORCE.get(),
                        ModItems.VAMPIRE_BLOOD.get(),
                        ModItems.LUCKY_ROCK.get(),
                        ModItems.PUFFER_FISH_LIVER.get(),
                        ModItems.RAGE_MIND.get(),
                        ModItems.TICK.get(),
                        ModItems.BLINDFOLD.get(),
                        ModItems.EXPLOSION_PROOF_JACKET.get(),
                        ModItems.CRACKED_CROWN.get(),
                        ModItems.GHAST_EYE.get(),
                        ModItems.WOODEN_STICK.get(),
                        ModItems.BLAZE_NUCLEUS.get(),
                        ModItems.ICE_CUBE.get(),
                        ModItems.SIGIL_OF_BAPHOMET.get(),
                        ModItems.CREEPER_SENSE.get(),
                        ModItems.FERTILIZER.get(),
                        ModItems.GODS_CROWN.get(),
                        ModItems.TEAR_OF_THE_SEA.get(),
                        ModItems.AMPHIBIOUS_HANDS.get(),
                        ModItems.GILLS.get(),
                        ModItems.TRUE_HEART_OF_THE_SEA.get(),
                        ModItems.MOON_STONE.get(),
                        ModItems.SLEEPING_PILLS.get(),
                        ModItems.ETHEREAL_WINGS.get(),
                        ModItems.SPIDER_LEGS.get(),
                        ModItems.REFORGER.get(),
                        ModItems.ELECTRIC_PADDLE.get(),
                        ModItems.FRACTURED_NULLSTONE.get(),
                        ModItems.POCKET_LIGHTNING_ROD.get(),
                        ModItems.FRAGILE_CLOUD.get(),
                        ModItems.SCARAB_AMULET.get(),
                        ModItems.FATE_EMERALD.get(),
                        ModItems.LIGHT_GLOVES.get(),
                        ModItems.DRAGONS_EYE.get(),
                        ModItems.FOUR_LEAF_CLOVER.get(),
                        ModItems.NELUMBO.get(),
                        ModItems.DARK_NELUMBO.get(),
                        ModItems.MINERS_SOUL.get(),
                        ModItems.SHRINKING_VEIL.get(),
                        ModItems.TITANS_MARK.get(),
                        ModItems.WOUNDBEARER.get(),
                        ModItems.RESONANT_HEART.get(),
                        ModItems.DYING_STAR.get()
                );

        tag(ModTags.RECYCLABLE_TRINKETS_TAG)
                .add(
                        ModItems.MISSING_PAGE.get(),
                        ModItems.REVERSE_CARD.get(),
                        ModItems.EXPERIENCE_BATTERY.get(),
                        ModItems.BROKEN_ANKH.get(),
                        ModItems.EXPERIENCE_MAGNET.get(),
                        ModItems.BROKEN_MAGNET.get(),
                        ModItems.SUPER_MAGNET.get(),
                        ModItems.WHAT_MAGNET.get(),
                        ModItems.CALLUS.get(),
                        ModItems.SPEED_FORCE.get(),
                        ModItems.VAMPIRE_BLOOD.get(),
                        ModItems.LUCKY_ROCK.get(),
                        ModItems.PUFFER_FISH_LIVER.get(),
                        ModItems.RAGE_MIND.get(),
                        ModItems.TICK.get(),
                        ModItems.BLINDFOLD.get(),
                        ModItems.EXPLOSION_PROOF_JACKET.get(),
                        ModItems.CRACKED_CROWN.get(),
                        ModItems.GHAST_EYE.get(),
                        ModItems.WOODEN_STICK.get(),
                        ModItems.BLAZE_NUCLEUS.get(),
                        ModItems.ICE_CUBE.get(),
                        ModItems.SIGIL_OF_BAPHOMET.get(),
                        ModItems.CREEPER_SENSE.get(),
                        ModItems.FERTILIZER.get(),
                        ModItems.GODS_CROWN.get(),
                        ModItems.TEAR_OF_THE_SEA.get(),
                        ModItems.AMPHIBIOUS_HANDS.get(),
                        ModItems.GILLS.get(),
                        ModItems.TRUE_HEART_OF_THE_SEA.get(),
                        ModItems.MOON_STONE.get(),
                        ModItems.SLEEPING_PILLS.get(),
                        ModItems.ETHEREAL_WINGS.get(),
                        ModItems.SPIDER_LEGS.get(),
                        ModItems.REFORGER.get(),
                        ModItems.ELECTRIC_PADDLE.get(),
                        ModItems.FRACTURED_NULLSTONE.get(),
                        ModItems.POCKET_LIGHTNING_ROD.get(),
                        ModItems.FRAGILE_CLOUD.get(),
                        ModItems.SCARAB_AMULET.get(),
                        ModItems.FATE_EMERALD.get(),
                        ModItems.LIGHT_GLOVES.get(),
                        ModItems.DRAGONS_EYE.get(),
                        ModItems.FOUR_LEAF_CLOVER.get(),
                        ModItems.NELUMBO.get(),
                        ModItems.DARK_NELUMBO.get(),
                        ModItems.MINERS_SOUL.get(),
                        ModItems.SHRINKING_VEIL.get(),
                        ModItems.TITANS_MARK.get(),
                        ModItems.WOUNDBEARER.get(),
                        ModItems.RESONANT_HEART.get(),
                        ModItems.DYING_STAR.get()
                );
    }
}
