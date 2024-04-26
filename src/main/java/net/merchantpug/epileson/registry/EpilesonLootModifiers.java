package net.merchantpug.epileson.registry;

import com.mojang.serialization.Codec;
import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.loot.AddPoolLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EpilesonLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Epileson.MOD_ID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_EPILESON_UPGRADE_SMITHING_TEMPLATE = LOOT_MODIFIERS.register("add_epileson_upgrade_smithing_template", () -> AddPoolLootModifier.CODEC);

    public static void registerLootModifiers(IEventBus eventBus){
        LOOT_MODIFIERS.register(eventBus);
    }
}
