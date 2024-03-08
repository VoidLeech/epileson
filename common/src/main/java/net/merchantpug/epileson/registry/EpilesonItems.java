package net.merchantpug.epileson.registry;

import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.item.EpilesonArmorItem;
import net.merchantpug.epileson.registry.internal.RegistrationFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.List;

public class EpilesonItems {
    public static final ArmorItem EPILESON_HELMET = createEpilesonArmor(ArmorItem.Type.HELMET);
    public static final ArmorItem EPILESON_CHESTPLATE = createEpilesonArmor(ArmorItem.Type.CHESTPLATE);
    public static final ArmorItem EPILESON_LEGGINGS = createEpilesonArmor(ArmorItem.Type.LEGGINGS);
    public static final ArmorItem EPILESON_BOOTS = createEpilesonArmor(ArmorItem.Type.BOOTS);
    public static final Item EPILESON_SHEET = new Item(new Item.Properties());
    public static final SmithingTemplateItem EPILESON_UPGRADE_SMITHING_TEMPLATE = createEpilesonUpgradeSmithingTemplate();

    public static void registerItems(RegistrationFunction<Item> function) {
        function.register(BuiltInRegistries.ITEM, Epileson.asResource("epileson_helmet"), EPILESON_HELMET);
        function.register(BuiltInRegistries.ITEM, Epileson.asResource("epileson_chestplate"), EPILESON_CHESTPLATE);
        function.register(BuiltInRegistries.ITEM, Epileson.asResource("epileson_leggings"), EPILESON_LEGGINGS);
        function.register(BuiltInRegistries.ITEM, Epileson.asResource("epileson_boots"), EPILESON_BOOTS);
        function.register(BuiltInRegistries.ITEM, Epileson.asResource("epileson_sheet"), EPILESON_SHEET);
        function.register(BuiltInRegistries.ITEM, Epileson.asResource("epileson_upgrade_smithing_template"), EPILESON_UPGRADE_SMITHING_TEMPLATE);
    }

    private static ArmorItem createEpilesonArmor(ArmorItem.Type type) {
        return new EpilesonArmorItem(EpilesonArmorMaterials.EPILESON, type, new Item.Properties());
    }

    private static SmithingTemplateItem createEpilesonUpgradeSmithingTemplate() {
        return new SmithingTemplateItem(Component.translatable(
                        Util.makeDescriptionId("item", Epileson.asResource("smithing_template.epileson_upgrade.applies_to"))
                ).withStyle(ChatFormatting.BLUE),
                Component.translatable(
                        Util.makeDescriptionId("item", Epileson.asResource("smithing_template.epileson_upgrade.ingredients"))
                ).withStyle(ChatFormatting.BLUE),
                Component.translatable(Util.makeDescriptionId("upgrade", Epileson.asResource("epileson_upgrade")))
                        .withStyle(ChatFormatting.GRAY),
                Component.translatable(
                        Util.makeDescriptionId("item", Epileson.asResource("smithing_template.epileson_upgrade.base_slot_description"))
                ),
                Component.translatable(
                        Util.makeDescriptionId("item", Epileson.asResource("smithing_template.epileson_upgrade.additions_slot_description"))
                ),
                List.of(
                        new ResourceLocation("item/empty_armor_slot_helmet"),
                        new ResourceLocation("item/empty_armor_slot_chestplate"),
                        new ResourceLocation("item/empty_armor_slot_leggings"),
                        new ResourceLocation("item/empty_armor_slot_boots")
                ),
                List.of(Epileson.asResource("item/empty_slot_sheet")));
    }

    public static void addAfterCombatTab(BiConsumer<ItemLike, List<ItemStack>> consumer) {
        consumer.accept(Items.NETHERITE_BOOTS, List.of(new ItemStack(EPILESON_HELMET), new ItemStack(EPILESON_CHESTPLATE), new ItemStack(EPILESON_LEGGINGS), new ItemStack(EPILESON_BOOTS)));
    }

    public static void addAfterIngredientsTab(BiConsumer<ItemLike, List<ItemStack>> consumer) {
        consumer.accept(Items.NETHERITE_INGOT, List.of(new ItemStack(EPILESON_SHEET)));
        consumer.accept(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, List.of(new ItemStack(EPILESON_UPGRADE_SMITHING_TEMPLATE)));
    }
}
