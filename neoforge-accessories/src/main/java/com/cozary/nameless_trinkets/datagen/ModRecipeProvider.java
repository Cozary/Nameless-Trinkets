package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMPHIBIOUS_HANDS.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.KELP)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLAZE_NUCLEUS.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Items.BLAZE_ROD)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLINDFOLD.get())
                .pattern("#G#")
                .pattern("GSG")
                .pattern("#G#")
                .define('#', Items.LEATHER)
                .define('G', Items.GOLDEN_CARROT)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BROKEN_ANKH.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern(" # ")
                .define('#', Items.BONE)
                .define('W', Items.WITHER_SKELETON_SKULL)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BROKEN_MAGNET.get())
                .pattern("iLi")
                .pattern("RSL")
                .pattern("iRi")
                .define('i', Items.IRON_INGOT)
                .define('L', Items.LAPIS_LAZULI)
                .define('R', Items.REDSTONE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CALLUS.get())
                .pattern("#C#")
                .pattern("HSH")
                .pattern("###")
                .define('#', Items.ROTTEN_FLESH)
                .define('C', Items.COBBLESTONE)
                .define('H', Items.HAY_BLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRACKED_CROWN.get())
                .pattern("#N#")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.GOLD_BLOCK)
                .define('N', Items.NETHERITE_SCRAP)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CREEPER_SENSE.get())
                .pattern("###")
                .pattern("TST")
                .pattern("###")
                .define('#', Items.GUNPOWDER)
                .define('T', Items.TNT)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('M', Items.MAGMA_BLOCK)
                .define('C', Items.CRIMSON_STEM)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_crimson"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('M', Items.MAGMA_BLOCK)
                .define('C', Items.STRIPPED_WARPED_STEM)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_stripped_warped"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('M', Items.MAGMA_BLOCK)
                .define('C', Items.CRIMSON_STEM)
                .define('S', ModItems.NELUMBO.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_crimson_nelumbo"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DARK_NELUMBO.get())
                .pattern("#M#")
                .pattern("CSC")
                .pattern("#C#")
                .define('#', Items.NETHER_WART)
                .define('M', Items.MAGMA_BLOCK)
                .define('C', Items.STRIPPED_WARPED_STEM)
                .define('S', ModItems.NELUMBO.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, "dark_nelumbo_stripped_warped_nelumbo"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRAGONS_EYE.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Items.ENDER_EYE)
                .define('W', Items.END_STONE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DUBIOUS_DUST.get())
                .pattern(" # ")
                .pattern("gRi")
                .pattern(" G ")
                .define('#', Items.IRON_PICKAXE)
                .define('g', Items.GOLD_INGOT)
                .define('R', Items.REDSTONE_BLOCK)
                .define('i', Items.IRON_INGOT)
                .define('G', Items.GUNPOWDER)
                .unlockedBy("has_iron_pickaxe", has(Items.IRON_PICKAXE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYING_STAR.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.NETHER_STAR)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ELECTRIC_PADDLE.get())
                .pattern("#&#")
                .pattern("&S&")
                .pattern("#&#")
                .define('#', Items.OAK_BOAT)
                .define('&', Items.REDSTONE_BLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ETHEREAL_WINGS.get())
                .pattern("#G#")
                .pattern("GSG")
                .pattern("#P#")
                .define('#', Items.FEATHER)
                .define('G', Items.GLASS_PANE)
                .define('P', Items.PHANTOM_MEMBRANE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXPERIENCE_BATTERY.get())
                .pattern("#R#")
                .pattern("#S#")
                .pattern("#i#")
                .define('#', Items.GLASS_BOTTLE)
                .define('R', Items.REDSTONE)
                .define('i', Items.IRON_INGOT)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXPERIENCE_MAGNET.get())
                .pattern("iGi")
                .pattern("RSR")
                .pattern("iRi")
                .define('G', Items.GLASS_BOTTLE)
                .define('R', Items.REDSTONE)
                .define('i', Items.IRON_INGOT)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXPLOSION_PROOF_JACKET.get())
                .pattern("iLi")
                .pattern("LSL")
                .pattern("iLi")
                .define('i', Items.IRON_INGOT)
                .define('L', Items.LEATHER_CHESTPLATE)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FATE_EMERALD.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Items.EMERALD_BLOCK)
                .define('W', Items.BELL)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FERTILIZER.get())
                .pattern("###")
                .pattern("LSL")
                .pattern("LLL")
                .define('#', Items.BONE_BLOCK)
                .define('L', Items.LEATHER)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FOUR_LEAF_CLOVER.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Items.DIAMOND_BLOCK)
                .define('W', Items.SHORT_GRASS)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FRACTURED_NULLSTONE.get())
                .pattern("#&#")
                .pattern("&S&")
                .pattern("#&#")
                .define('#', Items.GLOWSTONE)
                .define('&', Items.LAPIS_BLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FRAGILE_CLOUD.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Items.WHITE_WOOL)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GHAST_EYE.get())
                .pattern("#G#")
                .pattern("GSG")
                .pattern("#G#")
                .define('#', Items.REDSTONE_BLOCK)
                .define('G', Items.GHAST_TEAR)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GILLS.get())
                .pattern(" # ")
                .pattern("TSK")
                .pattern(" P ")
                .define('#', Items.COD)
                .define('T', Items.TROPICAL_FISH)
                .define('K', Items.SALMON)
                .define('P', Items.PUFFERFISH)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLOWING_DUST.get())
                .pattern("#G#")
                .pattern("/Q/")
                .pattern("#G#")
                .define('#', ModItems.DUBIOUS_DUST.get())
                .define('G', Items.GLOWSTONE)
                .define('/', Items.BLAZE_ROD)
                .define('Q', Items.QUARTZ)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ICE_CUBE.get())
                .pattern("#S#")
                .pattern("SPS")
                .pattern("#S#")
                .define('#', Items.ICE)
                .define('S', Items.SNOW_BLOCK)
                .define('P', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LIGHT_GLOVES.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#W#")
                .define('#', Items.GOLD_BLOCK)
                .define('W', Items.DIAMOND)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LUCKY_ROCK.get())
                .pattern("#R#")
                .pattern("iSg")
                .pattern("#C#")
                .define('#', Items.COBBLESTONE)
                .define('R', Items.REDSTONE)
                .define('i', Items.IRON_INGOT)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .define('g', Items.GOLD_INGOT)
                .define('C', Items.COAL)
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MINERS_SOUL.get())
                .pattern("#R#")
                .pattern("iSg")
                .pattern("#C#")
                .define('#', Items.BLACKSTONE)
                .define('R', Items.EMERALD)
                .define('i', Items.LAPIS_LAZULI)
                .define('g', Items.DIAMOND)
                .define('C', Items.COAL)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MISSING_PAGE.get())
                .pattern("###")
                .pattern("PSP")
                .pattern("BBB")
                .define('#', Items.ROTTEN_FLESH)
                .define('P', Items.PAPER)
                .define('B', Items.BONE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOON_STONE.get())
                .pattern("###")
                .pattern("SKS")
                .pattern("###")
                .define('#', Items.FEATHER)
                .define('S', Items.SPONGE)
                .define('K', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MYSTERIOUS_TRINKET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.UNKNOWN_FRAGMENT.get())
                .unlockedBy("has_unknown_fragment_dust", has(ModItems.UNKNOWN_FRAGMENT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NELUMBO.get())
                .pattern("#S#")
                .pattern("OQO")
                .pattern("#O#")
                .define('#', Items.LILY_PAD)
                .define('S', Items.SPONGE)
                .define('O', Items.OAK_LOG)
                .define('Q', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.POCKET_LIGHTNING_ROD.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Items.STRING)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REFORGER.get())
                .pattern("###")
                .pattern("ISI")
                .pattern(" I ")
                .define('#', Items.GOLD_BLOCK)
                .define('I', Items.IRON_INGOT)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RESONANT_HEART.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('#', Items.SCULK_SENSOR)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REVERSE_CARD.get())
                .pattern("#i#")
                .pattern("#S#")
                .pattern("#i#")
                .define('#', Items.SHIELD)
                .define('i', Items.IRON_INGOT)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCARAB_AMULET.get())
                .pattern("///")
                .pattern("/S/")
                .pattern("///")
                .define('/', Items.CHISELED_SANDSTONE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHRINKING_VEIL.get())
                .pattern(" L ")
                .pattern("FSF")
                .pattern(" L ")
                .define('L', Items.LEATHER)
                .define('F', Items.FEATHER)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SIGIL_OF_BAPHOMET.get())
                .pattern("#W#")
                .pattern("#S#")
                .pattern("#N#")
                .define('#', Items.OBSIDIAN)
                .define('W', Items.WITHER_SKELETON_SKULL)
                .define('N', Items.NETHERITE_SCRAP)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SLEEPING_PILLS.get())
                .pattern("#SP")
                .pattern("GYG")
                .pattern("SFS")
                .define('#', Items.DANDELION)
                .define('S', Items.SUGAR)
                .define('P', Items.POPPY)
                .define('G', Items.GOLDEN_CARROT)
                .define('F', Items.FERMENTED_SPIDER_EYE)
                .define('Y', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPEED_FORCE.get())
                .pattern("###")
                .pattern("#SR")
                .pattern("###")
                .define('#', Items.SUGAR)
                .define('R', Items.RABBIT_FOOT)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPIDER_LEGS.get())
                .pattern("#S#")
                .pattern("RUR")
                .pattern("#R#")
                .define('#', Items.COBWEB)
                .define('S', Items.SPIDER_EYE)
                .define('R', Items.STRING)
                .define('U', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SUPER_MAGNET.get())
                .pattern("iIi")
                .pattern("LDS")
                .pattern("iIi")
                .define('i', Items.IRON_INGOT)
                .define('I', Items.IRON_BLOCK)
                .define('L', ModItems.EXPERIENCE_MAGNET.get())
                .define('S', ModItems.BROKEN_MAGNET.get())
                .define('D', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TEAR_OF_THE_SEA.get())
                .pattern(" # ")
                .pattern("TSC")
                .pattern(" W ")
                .define('#', Items.SALMON)
                .define('T', Items.TROPICAL_FISH)
                .define('C', Items.COD)
                .define('W', Items.WATER_BUCKET)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TICK.get())
                .pattern("#HR")
                .pattern("HSH")
                .pattern("RH#")
                .define('#', Items.BROWN_MUSHROOM)
                .define('H', Items.HAY_BLOCK)
                .define('R', Items.RED_MUSHROOM)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TITANS_MARK.get())
                .pattern("XIX")
                .pattern("ISI")
                .pattern("XIX")
                .define('I', Items.REDSTONE)
                .define('X', Items.GUNPOWDER)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TRINKET_BUNDLE.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("###")
                .define('S', Items.BUNDLE)
                .define('#', ModItems.UNKNOWN_FRAGMENT.get())
                .unlockedBy("has_unknown_fragment", has(ModItems.UNKNOWN_FRAGMENT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TRUE_HEART_OF_THE_SEA.get())
                .pattern(" # ")
                .pattern("PSM")
                .pattern(" Y ")
                .define('#', Items.HEART_OF_THE_SEA)
                .define('P', ModItems.GILLS.get())
                .define('S', ModItems.ULTIMATE_DUST.get())
                .define('M', ModItems.TEAR_OF_THE_SEA.get())
                .define('Y', ModItems.AMPHIBIOUS_HANDS.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ULTIMATE_DUST.get())
                .pattern("#S#")
                .pattern("SNS")
                .pattern("#S#")
                .define('#', Items.ENDER_EYE)
                .define('N', Items.NETHERITE_SCRAP)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VAMPIRE_BLOOD.get())
                .pattern("#C#")
                .pattern("BSB")
                .pattern("#Q#")
                .define('#', Items.REDSTONE_BLOCK)
                .define('C', Items.CRYING_OBSIDIAN)
                .define('B', Items.BLAZE_POWDER)
                .define('Q', Items.CLOCK)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WHAT_MAGNET.get())
                .pattern("iRi")
                .pattern("PSL")
                .pattern("iBi")
                .define('i', Items.IRON_INGOT)
                .define('R', Items.ROTTEN_FLESH)
                .define('P', Items.PORKCHOP)
                .define('L', Items.LEATHER)
                .define('B', Items.BONE)
                .define('S', ModItems.GLOWING_DUST.get())
                .unlockedBy("has_glowing_dust", has(ModItems.GLOWING_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_STICK.get())
                .pattern("g/g")
                .pattern("/S/")
                .pattern("g/g")
                .define('g', Items.GOLD_INGOT)
                .define('/', Items.STICK)
                .define('S', ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOUNDBEARER.get())
                .pattern("###")
                .pattern("#S#")
                .pattern("#X#")
                .define('#', Items.BLAZE_ROD)
                .define('X', Items.NETHERITE_INGOT)
                .define('S', ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(new ItemLike[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}),
                Ingredient.of(Items.NETHER_STAR),
                Ingredient.of(ModItems.CRACKED_CROWN.get()),
                        RecipeCategory.MISC, ModItems.GODS_CROWN.get())
                .unlocks("has_netherite_ingot", has((ItemLike)Items.NETHER_STAR))
                .save(output, ResourceLocation.fromNamespaceAndPath(NamelessTrinkets.MOD_ID, getItemName(ModItems.GODS_CROWN.get())));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PUFFER_FISH_LIVER.get())
                .requires(Items.PUFFERFISH)
                .requires(ModItems.DUBIOUS_DUST.get())
                .unlockedBy("has_dubious_dust", has(ModItems.DUBIOUS_DUST.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAGE_MIND.get())
                .requires(Items.REDSTONE_BLOCK)
                .requires(Items.CRYING_OBSIDIAN)
                .requires(Items.REDSTONE_BLOCK)
                .requires(Items.DIAMOND)
                .requires(Items.DIAMOND)
                .requires(Items.REDSTONE_BLOCK)
                .requires(Items.MAGMA_BLOCK)
                .requires(Items.REDSTONE_BLOCK)
                .requires(ModItems.ULTIMATE_DUST.get())
                .unlockedBy("has_ultimate_dust", has(ModItems.ULTIMATE_DUST.get()))
                .save(output);
    }
}