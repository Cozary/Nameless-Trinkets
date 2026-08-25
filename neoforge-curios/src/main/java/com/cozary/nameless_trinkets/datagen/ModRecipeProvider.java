package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.SmithingTransformRecipeBuilder.smithing;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.MISC, ModItems.TICK.get())
                .pattern("#HR")
                .pattern("HSH")
                .pattern("RH#")
                .define('#', Items.BROWN_MUSHROOM)
                .define('H', Blocks.HAY_BLOCK)
                .define('R', Items.RED_MUSHROOM)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.GILLS.get())
                .pattern(" # ")
                .pattern("TSK")
                .pattern(" P ")
                .define('#', Items.COD)
                .define('T', Items.TROPICAL_FISH)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('K', Items.SALMON)
                .define('P', Items.PUFFERFISH)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.CALLUS.get())
                .pattern("#C#")
                .pattern("HSH")
                .pattern("###")
                .define('#', Items.ROTTEN_FLESH)
                .define('C', Blocks.COBBLESTONE)
                .define('H', Blocks.HAY_BLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.NELUMBO.get())
                .pattern("#S#")
                .pattern("OQO")
                .pattern("#O#")
                .define('#', Blocks.LILY_PAD)
                .define('S', Blocks.SPONGE)
                .define('O', Blocks.OAK_LOG)
                .define('Q', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.ICE_CUBE.get())
                .pattern("#S#")
                .pattern("SPS")
                .pattern("#S#")
                .define('#', Blocks.ICE)
                .define('S', Blocks.SNOW_BLOCK)
                .define('P', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.REFORGER.get())
                .pattern("###")
                .pattern("ISI")
                .pattern(" I ")
                .define('#', Blocks.GOLD_BLOCK)
                .define('I', Items.IRON_INGOT)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.BLINDFOLD.get())
                .pattern("#G#")
                .pattern("GSG")
                .pattern("#G#")
                .define('#', Items.LEATHER)
                .define('G', Items.GOLDEN_CARROT)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.WOODEN_STICK.get())
                .pattern("g/g")
                .pattern("/S/")
                .pattern("g/g")
                .define('g', Items.GOLD_INGOT)
                .define('/', Items.STICK)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.BLAZE_NUCLEUS.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Items.BLAZE_ROD)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.BROKEN_MAGNET.get())
                .pattern("iLi")
                .pattern("RSL")
                .pattern("iRi")
                .define('i', Items.IRON_INGOT)
                .define('L', Items.LAPIS_LAZULI)
                .define('R', Items.REDSTONE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.GHAST_EYE.get())
                .pattern("#G#")
                .pattern("GSG")
                .pattern("#G#")
                .define('#', Blocks.REDSTONE_BLOCK)
                .define('G', Items.GHAST_TEAR)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, ModItems.RAGE_MIND.get())
                .requires(Blocks.REDSTONE_BLOCK, 4)
                .requires(Blocks.CRYING_OBSIDIAN)
                .requires(Items.DIAMOND, 2)
                .requires(Blocks.MAGMA_BLOCK)
                .requires(ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.DYING_STAR.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.NETHER_STAR)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.FERTILIZER.get())
                .pattern("###")
                .pattern("LSL")
                .pattern("LLL")
                .define('#', Blocks.BONE_BLOCK)
                .define('L', Items.LEATHER)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(Items.NETHER_STAR),
                Ingredient.of(ModItems.CRACKED_CROWN.get()),
                RecipeCategory.MISC,
                ModItems.GODS_CROWN.get()
        )
                .unlocks("has_cracked_crown", has(ModItems.CRACKED_CROWN.get()))
                .save(output, Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "gods_crown").toString());

        shaped(RecipeCategory.MISC, ModItems.LUCKY_ROCK.get())
                .pattern("#R#")
                .pattern("iSg")
                .pattern("#C#")
                .define('#', Blocks.COBBLESTONE)
                .define('C', Items.COAL)
                .define('R', Items.REDSTONE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('g', Items.GOLD_INGOT)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.MOON_STONE.get())
                .pattern("###")
                .pattern("SKS")
                .pattern("###")
                .define('#', Items.FEATHER)
                .define('K', ModItems.DUBIOUS_DUST.get())
                .define('S', Blocks.SPONGE)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.BROKEN_ANKH.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.BONE)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('W', Items.WITHER_SKELETON_SKULL)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.DRAGONS_EYE.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Items.ENDER_EYE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('W', Blocks.END_STONE)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.MINERS_SOUL.get())
                .pattern("#R#")
                .pattern("iSg")
                .pattern("#C#")
                .define('#', Blocks.BLACKSTONE)
                .define('C', Items.COAL)
                .define('R', Items.EMERALD)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('g', Items.DIAMOND)
                .define('i', Items.LAPIS_LAZULI)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SPEED_FORCE.get())
                .pattern("###")
                .pattern("#SR")
                .pattern("###")
                .define('#', Items.SUGAR)
                .define('R', Items.RABBIT_FOOT)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SPIDER_LEGS.get())
                .pattern("#S#")
                .pattern("RUR")
                .pattern("#R#")
                .define('#', Blocks.COBWEB)
                .define('R', Items.STRING)
                .define('S', Items.SPIDER_EYE)
                .define('U', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.TITANS_MARK.get())
                .pattern("XIX")
                .pattern("ISI")
                .pattern("XIX")
                .define('I', Items.REDSTONE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('X', Items.GUNPOWDER)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.WHAT_MAGNET.get())
                .pattern("iRi")
                .pattern("PSL")
                .pattern("iBi")
                .define('B', Items.BONE)
                .define('L', Items.LEATHER)
                .define('P', Items.PORKCHOP)
                .define('R', Items.ROTTEN_FLESH)
                .define('S', ModItems.GLOWING_DUST.get())
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.WOUNDBEARER.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("#X#")
                .define('#', Items.BLAZE_ROD)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('X', Items.NETHERITE_INGOT)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.DUBIOUS_DUST.get())
                .pattern(" # ")
                .pattern("gRi")
                .pattern(" G ")
                .define('#', Items.IRON_PICKAXE)
                .define('G', Items.GUNPOWDER)
                .define('R', Blocks.REDSTONE_BLOCK)
                .define('g', Items.GOLD_INGOT)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_redstone_block", has(Blocks.REDSTONE_BLOCK))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.FATE_EMERALD.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Blocks.EMERALD_BLOCK)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('W', Blocks.BELL)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.GLOWING_DUST.get())
                .pattern("#G#")
                .pattern("/Q/")
                .pattern("#G#")
                .define('#', ModItems.DUBIOUS_DUST.get())
                .define('/', Items.BLAZE_ROD)
                .define('G', Items.GLOWSTONE_DUST)
                .define('Q', Items.QUARTZ)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.LIGHT_GLOVES.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Blocks.GOLD_BLOCK)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('W', Items.DIAMOND)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.MISSING_PAGE.get())
                .pattern("###")
                .pattern("PSP")
                .pattern("BBB")
                .define('#', Items.ROTTEN_FLESH)
                .define('B', Items.BONE)
                .define('P', Items.PAPER)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.REVERSE_CARD.get())
                .pattern("#i#")
                .pattern("#S#")
                .pattern("#i#")
                .define('#', Items.SHIELD)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SUPER_MAGNET.get())
                .pattern("iIi")
                .pattern("LDS")
                .pattern("iIi")
                .define('D', ModItems.GLOWING_DUST.get())
                .define('I', Blocks.IRON_BLOCK)
                .define('L', ModItems.EXPERIENCE_MAGNET.get())
                .define('S', ModItems.BROKEN_MAGNET.get())
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.CRACKED_CROWN.get())
                .pattern("#N#")
                .pattern("#S#")
                .pattern("###")
                .define('#', Blocks.GOLD_BLOCK)
                .define('N', Items.NETHERITE_SCRAP)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.CREEPER_SENSE.get())
                .pattern("###")
                .pattern("TST")
                .pattern("###")
                .define('#', Items.GUNPOWDER)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('T', Blocks.TNT)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.FRAGILE_CLOUD.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Blocks.WHITE_WOOL)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SCARAB_AMULET.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Blocks.CHISELED_SANDSTONE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.ULTIMATE_DUST.get())
                .pattern("#S#")
                .pattern("SNS")
                .pattern("#S#")
                .define('#', Items.ENDER_EYE)
                .define('N', Items.NETHERITE_SCRAP)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.VAMPIRE_BLOOD.get())
                .pattern("#C#")
                .pattern("BSB")
                .pattern("#Q#")
                .define('#', Blocks.REDSTONE_BLOCK)
                .define('B', Items.BLAZE_POWDER)
                .define('C', Blocks.CRYING_OBSIDIAN)
                .define('Q', Items.CLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.ETHEREAL_WINGS.get())
                .pattern("#G#")
                .pattern("GSG")
                .pattern("#P#")
                .define('#', Items.FEATHER)
                .define('G', Blocks.GLASS_PANE)
                .define('P', Items.PHANTOM_MEMBRANE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.RESONANT_HEART.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Blocks.SCULK_SENSOR)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SHRINKING_VEIL.get())
                .pattern(" L ")
                .pattern("FSF")
                .pattern(" L ")
                .define('F', Items.FEATHER)
                .define('L', Items.LEATHER)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SLEEPING_PILLS.get())
                .pattern("#SP")
                .pattern("GYG")
                .pattern("SFS")
                .define('#', Blocks.DANDELION)
                .define('F', Items.FERMENTED_SPIDER_EYE)
                .define('G', Items.GOLDEN_CARROT)
                .define('P', Blocks.POPPY)
                .define('S', Items.SUGAR)
                .define('Y', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.ELECTRIC_PADDLE.get())
                .pattern("#&#")
                .pattern("&S&")
                .pattern("#&#")
                .define('#', Items.OAK_BOAT)
                .define('&', Blocks.REDSTONE_BLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.TEAR_OF_THE_SEA.get())
                .pattern(" # ")
                .pattern("TSC")
                .pattern(" W ")
                .define('#', Items.SALMON)
                .define('C', Items.COD)
                .define('S', ModItems.GLOWING_DUST.get())
                .define('T', Items.TROPICAL_FISH)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.AMPHIBIOUS_HANDS.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.KELP)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.FOUR_LEAF_CLOVER.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Blocks.DIAMOND_BLOCK)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('W', Blocks.SHORT_GRASS)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.EXPERIENCE_MAGNET.get())
                .pattern("iGi")
                .pattern("RSR")
                .pattern("iRi")
                .define('G', Items.GLASS_BOTTLE)
                .define('R', Items.REDSTONE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, ModItems.PUFFER_FISH_LIVER.get())
                .requires(Items.PUFFERFISH)
                .requires(ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.SIGIL_OF_BAPHOMET.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#N#")
                .define('#', Blocks.OBSIDIAN)
                .define('N', Items.NETHERITE_SCRAP)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('W', Items.WITHER_SKELETON_SKULL)
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.EXPERIENCE_BATTERY.get())
                .pattern("#R#")
                .pattern("#S#")
                .pattern("#i#")
                .define('#', Items.GLASS_BOTTLE)
                .define('R', Items.REDSTONE)
                .define('S', ModItems.GLOWING_DUST.get())
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.FRACTURED_NULLSTONE.get())
                .pattern("#&#")
                .pattern("&S&")
                .pattern("#&#")
                .define('#', Blocks.GLOWSTONE)
                .define('&', Blocks.LAPIS_BLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.POCKET_LIGHTNING_ROD.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Items.STRING)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.TRUE_HEART_OF_THE_SEA.get())
                .pattern(" # ")
                .pattern("PSM")
                .pattern(" Y ")
                .define('#', Items.HEART_OF_THE_SEA)
                .define('M', ModItems.TEAR_OF_THE_SEA.get())
                .define('P', ModItems.GILLS.get())
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('Y', ModItems.AMPHIBIOUS_HANDS.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.EXPLOSION_PROOF_JACKET.get())
                .pattern("iLi")
                .pattern("LSL")
                .pattern("iLi")
                .define('L', Items.LEATHER_CHESTPLATE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('C', Blocks.CRIMSON_STEM)
                .define('M', Blocks.MAGMA_BLOCK)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output, Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_crimson").toString());

        shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('C', Blocks.CRIMSON_STEM)
                .define('M', Blocks.MAGMA_BLOCK)
                .define('S', ModItems.NELUMBO.get())
                .unlockedBy("has_nelumbo", has(ModItems.NELUMBO.get()))
                .save(output, Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_crimson_nelumbo").toString());

        shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('C', Blocks.STRIPPED_WARPED_STEM)
                .define('M', Blocks.MAGMA_BLOCK)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output, Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_stripped_warped").toString());

        shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('C', Blocks.STRIPPED_WARPED_STEM)
                .define('M', Blocks.MAGMA_BLOCK)
                .define('S', ModItems.NELUMBO.get())
                .unlockedBy("has_nelumbo", has(ModItems.NELUMBO.get()))
                .save(output, Identifier.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_stripped_warped_nelumbo").toString());

        shaped(RecipeCategory.MISC, ModItems.TRINKET_BUNDLE.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', ModItems.UNKNOWN_FRAGMENT.get())
                .define('S', Items.BUNDLE)
                .unlockedBy("has_unknown_fragment", has(ModItems.UNKNOWN_FRAGMENT.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.MYSTERIOUS_TRINKET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.UNKNOWN_FRAGMENT.get())
                .unlockedBy("has_unknown_fragment", has(ModItems.UNKNOWN_FRAGMENT.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.LUNAR_CREST.get())
                .pattern("#B#")
                .pattern("BSB")
                .pattern("#B#")
                .define('#', Items.LAPIS_LAZULI)
                .define('B', Items.BONE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.ECLIPSE_ASHES.get())
                .pattern("#B#")
                .pattern("BSB")
                .pattern("#B#")
                .define('#', Items.BLAZE_POWDER)
                .define('B', Items.SOUL_SOIL)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Nameless Trinkets Recipes";
        }
    }
}
