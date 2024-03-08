package net.merchantpug.epileson.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.registry.EpilesonItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.data.advancements.packs.VanillaTheEndAdvancements;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class EpilesonDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EpilesonAdvancementProvider::new);
        pack.addProvider(EpilesonRecipeProvider::new);
    }

    public static class EpilesonAdvancementProvider extends FabricAdvancementProvider {

        protected EpilesonAdvancementProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateAdvancement(Consumer<AdvancementHolder> consumer) {
            consumer.accept(new Advancement.Builder()
                    .parent(new ResourceLocation("end/find_end_city"))
                    .addCriterion("epileson_sheet",
                            InventoryChangeTrigger.TriggerInstance.hasItems(
                                    EpilesonItems.EPILESON_SHEET))
                    .display(EpilesonItems.EPILESON_SHEET,
                            Component.translatable("advancements.epileson.end.obtain_epileson_sheet.title"),
                            Component.translatable("advancements.epileson.end.obtain_epileson_sheet.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    .build(Epileson.asResource("end/obtain_epileson_sheet")));
            consumer.accept(new Advancement.Builder()
                    .parent(Epileson.asResource("end/obtain_epileson_sheet"))
                    .addCriterion("epileson_armor",
                    InventoryChangeTrigger.TriggerInstance.hasItems(
                            EpilesonItems.EPILESON_HELMET,
                            EpilesonItems.EPILESON_CHESTPLATE,
                            EpilesonItems.EPILESON_LEGGINGS,
                            EpilesonItems.EPILESON_BOOTS))
                    .display(EpilesonItems.EPILESON_CHESTPLATE,
                            Component.translatable("advancements.epileson.end.epileson_armor.title"),
                            Component.translatable("advancements.epileson.end.epileson_armor.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false)
                    .build(Epileson.asResource("end/epileson_armor")));
        }
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
