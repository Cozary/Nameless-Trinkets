package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends ItemTagsProvider {


    public ModTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(packOutput, lookupProvider, blockTags, NamelessTrinkets.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTags.NAMELESS_TRINKETS_TAG)
                .add(ModItems.MISSING_PAGE.getResourceKey(),
                        ModItems.REVERSE_CARD.getResourceKey(),
                        ModItems.EXPERIENCE_BATTERY.getResourceKey(),
                        ModItems.BROKEN_ANKH.getResourceKey(),
                        ModItems.EXPERIENCE_MAGNET.getResourceKey(),
                        ModItems.BROKEN_MAGNET.getResourceKey(),
                        ModItems.SUPER_MAGNET.getResourceKey(),
                        ModItems.WHAT_MAGNET.getResourceKey(),
                        ModItems.CALLUS.getResourceKey(),
                        ModItems.SPEED_FORCE.getResourceKey(),
                        ModItems.VAMPIRE_BLOOD.getResourceKey(),
                        ModItems.LUCKY_ROCK.getResourceKey(),
                        ModItems.PUFFER_FISH_LIVER.getResourceKey(),
                        ModItems.RAGE_MIND.getResourceKey(),
                        ModItems.TICK.getResourceKey(),
                        ModItems.BLINDFOLD.getResourceKey(),
                        ModItems.EXPLOSION_PROOF_JACKET.getResourceKey(),
                        ModItems.CRACKED_CROWN.getResourceKey(),
                        ModItems.GHAST_EYE.getResourceKey(),
                        ModItems.WOODEN_STICK.getResourceKey(),
                        ModItems.BLAZE_NUCLEUS.getResourceKey(),
                        ModItems.ICE_CUBE.getResourceKey(),
                        ModItems.SIGIL_OF_BAPHOMET.getResourceKey(),
                        ModItems.CREEPER_SENSE.getResourceKey(),
                        ModItems.FERTILIZER.getResourceKey(),
                        ModItems.GODS_CROWN.getResourceKey(),
                        ModItems.TEAR_OF_THE_SEA.getResourceKey(),
                        ModItems.AMPHIBIOUS_HANDS.getResourceKey(),
                        ModItems.GILLS.getResourceKey(),
                        ModItems.TRUE_HEART_OF_THE_SEA.getResourceKey(),
                        ModItems.MOON_STONE.getResourceKey(),
                        ModItems.SLEEPING_PILLS.getResourceKey(),
                        ModItems.ETHEREAL_WINGS.getResourceKey(),
                        ModItems.SPIDER_LEGS.getResourceKey(),
                        ModItems.REFORGER.getResourceKey(),
                        ModItems.ELECTRIC_PADDLE.getResourceKey(),
                        ModItems.FRACTURED_NULLSTONE.getResourceKey(),
                        ModItems.POCKET_LIGHTNING_ROD.getResourceKey(),
                        ModItems.FRAGILE_CLOUD.getResourceKey(),
                        ModItems.SCARAB_AMULET.getResourceKey(),
                        ModItems.FATE_EMERALD.getResourceKey(),
                        ModItems.LIGHT_GLOVES.getResourceKey(),
                        ModItems.DRAGONS_EYE.getResourceKey(),
                        ModItems.FOUR_LEAF_CLOVER.getResourceKey(),
                        ModItems.NELUMBO.getResourceKey(),
                        ModItems.DARK_NELUMBO.getResourceKey(),
                        ModItems.MINERS_SOUL.getResourceKey(),
                        ModItems.SHRINKING_VEIL.getResourceKey(),
                        ModItems.TITANS_MARK.getResourceKey(),
                        ModItems.WOUNDBEARER.getResourceKey(),
                        ModItems.RESONANT_HEART.getResourceKey(),
                        ModItems.DYING_STAR.getResourceKey()
                );

        tag(ModTags.RECYCLABLE_TRINKETS_TAG)
                .add(ModItems.MISSING_PAGE.getResourceKey(),
                        ModItems.REVERSE_CARD.getResourceKey(),
                        ModItems.EXPERIENCE_BATTERY.getResourceKey(),
                        ModItems.BROKEN_ANKH.getResourceKey(),
                        ModItems.EXPERIENCE_MAGNET.getResourceKey(),
                        ModItems.BROKEN_MAGNET.getResourceKey(),
                        ModItems.SUPER_MAGNET.getResourceKey(),
                        ModItems.WHAT_MAGNET.getResourceKey(),
                        ModItems.CALLUS.getResourceKey(),
                        ModItems.SPEED_FORCE.getResourceKey(),
                        ModItems.VAMPIRE_BLOOD.getResourceKey(),
                        ModItems.LUCKY_ROCK.getResourceKey(),
                        ModItems.PUFFER_FISH_LIVER.getResourceKey(),
                        ModItems.RAGE_MIND.getResourceKey(),
                        ModItems.TICK.getResourceKey(),
                        ModItems.BLINDFOLD.getResourceKey(),
                        ModItems.EXPLOSION_PROOF_JACKET.getResourceKey(),
                        ModItems.CRACKED_CROWN.getResourceKey(),
                        ModItems.GHAST_EYE.getResourceKey(),
                        ModItems.WOODEN_STICK.getResourceKey(),
                        ModItems.BLAZE_NUCLEUS.getResourceKey(),
                        ModItems.ICE_CUBE.getResourceKey(),
                        ModItems.SIGIL_OF_BAPHOMET.getResourceKey(),
                        ModItems.CREEPER_SENSE.getResourceKey(),
                        ModItems.FERTILIZER.getResourceKey(),
                        ModItems.GODS_CROWN.getResourceKey(),
                        ModItems.TEAR_OF_THE_SEA.getResourceKey(),
                        ModItems.AMPHIBIOUS_HANDS.getResourceKey(),
                        ModItems.GILLS.getResourceKey(),
                        ModItems.TRUE_HEART_OF_THE_SEA.getResourceKey(),
                        ModItems.MOON_STONE.getResourceKey(),
                        ModItems.SLEEPING_PILLS.getResourceKey(),
                        ModItems.ETHEREAL_WINGS.getResourceKey(),
                        ModItems.SPIDER_LEGS.getResourceKey(),
                        ModItems.REFORGER.getResourceKey(),
                        ModItems.ELECTRIC_PADDLE.getResourceKey(),
                        ModItems.FRACTURED_NULLSTONE.getResourceKey(),
                        ModItems.POCKET_LIGHTNING_ROD.getResourceKey(),
                        ModItems.FRAGILE_CLOUD.getResourceKey(),
                        ModItems.SCARAB_AMULET.getResourceKey(),
                        ModItems.FATE_EMERALD.getResourceKey(),
                        ModItems.LIGHT_GLOVES.getResourceKey(),
                        ModItems.DRAGONS_EYE.getResourceKey(),
                        ModItems.FOUR_LEAF_CLOVER.getResourceKey(),
                        ModItems.NELUMBO.getResourceKey(),
                        ModItems.DARK_NELUMBO.getResourceKey(),
                        ModItems.MINERS_SOUL.getResourceKey(),
                        ModItems.SHRINKING_VEIL.getResourceKey(),
                        ModItems.TITANS_MARK.getResourceKey(),
                        ModItems.WOUNDBEARER.getResourceKey(),
                        ModItems.RESONANT_HEART.getResourceKey(),
                        ModItems.DYING_STAR.getResourceKey()
                );
    }

}