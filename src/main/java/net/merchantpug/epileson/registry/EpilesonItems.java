package net.merchantpug.epileson.registry;

import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.item.EpilesonArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.List;
import java.util.function.Supplier;

public class EpilesonItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Epileson.MOD_ID);

    public static final RegistryObject<ArmorItem> EPILESON_HELMET = ITEMS.register("epileson_helmet", createEpilesonArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<ArmorItem> EPILESON_CHESTPLATE = ITEMS.register("epileson_chestplate", createEpilesonArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<ArmorItem> EPILESON_LEGGINGS = ITEMS.register("epileson_leggings", createEpilesonArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<ArmorItem> EPILESON_BOOTS = ITEMS.register("epileson_boots", createEpilesonArmor(ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> EPILESON_SHEET = ITEMS.register("epileson_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<SmithingTemplateItem> EPILESON_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("epileson_upgrade_smithing_template", createEpilesonUpgradeSmithingTemplate());

    public static void registerItems(IEventBus bus) {
        ITEMS.register(bus);
    }

    private static Supplier<ArmorItem> createEpilesonArmor(ArmorItem.Type type) {
        return () -> new EpilesonArmorItem(EpilesonArmorMaterials.EPILESON, type, new Item.Properties());
    }

    private static Supplier<SmithingTemplateItem> createEpilesonUpgradeSmithingTemplate() {
        return () -> new SmithingTemplateItem(Component.translatable(
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
        consumer.accept(Items.NETHERITE_BOOTS, List.of(new ItemStack(EPILESON_HELMET.get()), new ItemStack(EPILESON_CHESTPLATE.get()), new ItemStack(EPILESON_LEGGINGS.get()), new ItemStack(EPILESON_BOOTS.get())));
    }

    public static void addAfterIngredientsTab(BiConsumer<ItemLike, List<ItemStack>> consumer) {
        consumer.accept(Items.NETHERITE_INGOT, List.of(new ItemStack(EPILESON_SHEET.get())));
        consumer.accept(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, List.of(new ItemStack(EPILESON_UPGRADE_SMITHING_TEMPLATE.get())));
    }
}
