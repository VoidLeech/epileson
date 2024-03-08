package net.merchantpug.epileson;

import net.merchantpug.epileson.registry.EpilesonItems;
import net.merchantpug.epileson.registry.EpilesonSoundEvents;
import net.merchantpug.epileson.registry.EpilesonTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Mod(Epileson.MOD_ID)
public class Epileson {

    public static final String MOD_ID = "epileson";
    public static final String MOD_NAME = "Epileson";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public Epileson() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EpilesonItems.registerItems(eventBus);
        EpilesonSoundEvents.registerSoundEvents(eventBus);
    }


    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static float reduceKineticDamageWithArmor(Iterable<ItemStack> armorItems, float damageValue) {
        float multiplier = 1.0F;
        for (ItemStack armorStack : armorItems) {
            if (armorStack.is(EpilesonTags.ItemTags.KINETIC_DAMAGE_RESISTANT)) {
                multiplier -= 0.05F;
            }
        }
        return damageValue * multiplier;
    }

    @Mod.EventBusSubscriber(modid = Epileson.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

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