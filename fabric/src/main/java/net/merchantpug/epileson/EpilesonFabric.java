package net.merchantpug.epileson;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.merchantpug.epileson.registry.EpilesonItems;
import net.merchantpug.epileson.registry.EpilesonSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTabs;

public class EpilesonFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Epileson.init();
        EpilesonItems.registerItems(Registry::register);
        EpilesonSoundEvents.registerSoundEvents(Registry::register);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> EpilesonItems.addAfterCombatTab(entries::addAfter));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> EpilesonItems.addAfterIngredientsTab(entries::addAfter));
    }
}
