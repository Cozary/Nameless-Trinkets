package com.cozary.nameless_trinkets.datagen;

import com.cozary.nameless_trinkets.NamelessTrinkets;
import com.cozary.nameless_trinkets.init.ModItems;
import com.cozary.nameless_trinkets.init.RegistryObject;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.Time;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, NamelessTrinkets.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (RegistryObject<Item> item : ModItems.CREATIVE_TAB_ITEMS) {
            if (item == ModItems.LUNAR_CREST) {
                generateLunarCrest(itemModels);
                continue;
            }
            itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
        }
    }

    private void generateLunarCrest(ItemModelGenerators itemModels) {
        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        ItemModel.Unbaked fallbackModel = null;
        for (int i = 0; i < 8; i++) {
            Identifier modelId = itemModels.createFlatItemModel(ModItems.LUNAR_CREST.get(), "_" + i, ModelTemplates.FLAT_ITEM);
            ItemModel.Unbaked model = ItemModelUtils.plainModel(modelId);
            if (i == 0) {
                fallbackModel = model;
            }
            entries.add(ItemModelUtils.override(model, i * 0.125F));
        }

        itemModels.itemModelOutput.accept(
                ModItems.LUNAR_CREST.get(),
                ItemModelUtils.rangeSelect(new Time(false, Time.TimeSource.MOON_PHASE), fallbackModel, entries)
        );
    }
}
