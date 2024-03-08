package net.merchantpug.epileson.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.merchantpug.epileson.registry.EpilesonItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class EpilesonDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EpilesonRecipeProvider::new);
    }

    public static class EpilesonRecipeProvider extends FabricRecipeProvider {
        public EpilesonRecipeProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void buildRecipes(RecipeOutput exporter) {
            epilesonSmithing(exporter, EpilesonItems.EPILESON_HELMET, Items.CHAINMAIL_HELMET);
            epilesonSmithing(exporter, EpilesonItems.EPILESON_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE);
            epilesonSmithing(exporter, EpilesonItems.EPILESON_LEGGINGS, Items.CHAINMAIL_LEGGINGS);
            epilesonSmithing(exporter, EpilesonItems.EPILESON_BOOTS, Items.CHAINMAIL_BOOTS);
            createEpilesonSheetRecipe(exporter);
            copySmithingTemplate(exporter);
        }

        private static void epilesonSmithing(RecipeOutput output, Item result, Item predecesor) {
            SmithingTransformRecipeBuilder.smithing(Ingredient.of(EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(predecesor), Ingredient.of(EpilesonItems.EPILESON_SHEET), RecipeCategory.COMBAT, result)
                    .unlocks("has_epileson_sheet", has(EpilesonItems.EPILESON_SHEET))
                    .save(output, getItemName(result) + "_smithing");
        }

        private static void createEpilesonSheetRecipe(RecipeOutput output) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EpilesonItems.EPILESON_SHEET)
                    .define('D', Items.DRAGON_BREATH)
                    .define('M', Items.PHANTOM_MEMBRANE)
                    .pattern("MD")
                    .pattern("DM")
                    .unlockedBy(getHasName(EpilesonItems.EPILESON_SHEET), has(EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE))
                    .save(output);
        }

        private static void copySmithingTemplate(RecipeOutput output) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE, 2)
                    .define('#', Items.DIAMOND)
                    .define('C', Items.OBSIDIAN)
                    .define('S', EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE)
                    .pattern("#S#")
                    .pattern("#C#")
                    .pattern("###")
                    .unlockedBy(getHasName(EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE), has(EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE))
                    .save(output);
        }

    }
}
