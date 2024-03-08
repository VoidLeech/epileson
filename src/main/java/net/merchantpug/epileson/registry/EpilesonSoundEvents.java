package net.merchantpug.epileson.registry;

import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.registry.internal.RegistrationFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EpilesonSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Epileson.MOD_ID);
    public static final RegistryObject<SoundEvent> ARMOR_EQUIP_EPILESON = SOUND_EVENTS.register("item.armor.equip_epileson", () -> SoundEvent.createVariableRangeEvent(Epileson.asResource("item.armor.equip_epileson")));

    public static void registerSoundEvents(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
