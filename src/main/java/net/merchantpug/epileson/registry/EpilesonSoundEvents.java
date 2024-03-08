package net.merchantpug.epileson.registry;

import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.registry.internal.RegistrationFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class EpilesonSoundEvents {
    public static final SoundEvent ARMOR_EQUIP_EPILESON = SoundEvent.createVariableRangeEvent(Epileson.asResource("item.armor.equip_epileson"));

    public static void registerSoundEvents(RegistrationFunction<SoundEvent> function) {
        function.register(BuiltInRegistries.SOUND_EVENT, Epileson.asResource("item.armor.equip_epileson"), ARMOR_EQUIP_EPILESON);
    }
}
