package net.merchantpug.epileson.registry;

import net.merchantpug.epileson.Epileson;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;

public class EpilesonTags {
    public static class ItemTags {
        public static final TagKey<Item> KINETIC_DAMAGE_RESISTANT = TagKey.create(Registries.ITEM, Epileson.asResource("kinetic_damage_resistant"));
    }

    public static class DamageTypeTags {
        public static final TagKey<DamageType> IS_KINETIC = TagKey.create(Registries.DAMAGE_TYPE, Epileson.asResource("is_kinetic"));
    }
}
