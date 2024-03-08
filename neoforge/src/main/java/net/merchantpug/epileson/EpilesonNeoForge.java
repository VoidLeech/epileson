package net.merchantpug.epileson;

import net.merchantpug.epileson.registry.EpilesonItems;
import net.merchantpug.epileson.registry.EpilesonSoundEvents;
import net.merchantpug.epileson.registry.EpilesonTags;
import net.merchantpug.epileson.registry.internal.RegistrationFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Mod(Epileson.MOD_ID)
public class EpilesonNeoForge {
    public EpilesonNeoForge(IEventBus eventBus) {
        Epileson.init();
    }

    @Mod.EventBusSubscriber(modid = Epileson.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void registerContent(RegisterEvent event) {
            if (event.getRegistryKey() == Registries.ITEM) {
                register(event, EpilesonItems::registerItems);
            } else if (event.getRegistryKey() == Registries.SOUND_EVENT) {
                register(event, EpilesonSoundEvents::registerSoundEvents);
            }
        }

        private static <T> void register(RegisterEvent event, Consumer<RegistrationFunction<T>> consumer) {
            consumer.accept((registry, id, value) -> event.register(registry.key(), id, () -> value));
        }

        @SubscribeEvent
        public static void onCreativeModeTabBuild(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                EpilesonItems.addAfterIngredientsTab((itemLike, stacks) -> addAfterCreativeTab(event, itemLike, stacks));
            } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
                EpilesonItems.addAfterCombatTab((itemLike, stacks) -> addAfterCreativeTab(event, itemLike, stacks));
            }
        }

        private static void addAfterCreativeTab(BuildCreativeModeTabContentsEvent event, ItemLike itemLike, List<ItemStack> stacks) {
            for (Map.Entry<ItemStack, CreativeModeTab.TabVisibility> entry : event.getEntries()) {
                if (entry.getKey().getItem() == itemLike.asItem()) {
                    ItemStack previousStack = entry.getKey();
                    for (ItemStack stack : stacks) {
                        event.getEntries().putAfter(previousStack, stack, entry.getValue());
                        previousStack = stack;
                    }
                    break;
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Epileson.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onLivingDamage(LivingDamageEvent event) {
            if (event.getSource().is(EpilesonTags.DamageTypeTags.IS_KINETIC)) {
                event.setAmount(Epileson.reduceKineticDamageWithArmor(event.getEntity().getArmorSlots(), event.getAmount()));
            }
        }
    }
}