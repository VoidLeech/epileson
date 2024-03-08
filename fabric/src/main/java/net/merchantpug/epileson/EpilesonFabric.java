package net.merchantpug.epileson;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.merchantpug.epileson.registry.EpilesonItems;
import net.merchantpug.epileson.registry.EpilesonSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class EpilesonFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Epileson.init();
        registerContent();
        modifyCreativeTabs();
        modifyLootTables();
    }

    private static void registerContent() {
        EpilesonItems.registerItems(Registry::register);
        EpilesonSoundEvents.registerSoundEvents(Registry::register);
    }

    private static void modifyCreativeTabs() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> EpilesonItems.addAfterCombatTab(entries::addAfter));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> EpilesonItems.addAfterIngredientsTab(entries::addAfter));
    }

    private static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && BuiltInLootTables.END_CITY_TREASURE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .with(EmptyLootItem.emptyItem().setWeight(2).build())
                        .with(LootItem.lootTableItem(EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE).setWeight(1).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
