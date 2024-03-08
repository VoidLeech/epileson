package net.merchantpug.epileson.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.epileson.Epileson;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.List;

public record IsSpecificLootTableCondition(List<ResourceLocation> lootTables) implements LootItemCondition {
    public static final ResourceLocation ID = Epileson.asResource("is_specific_loot_table");
    public static final Codec<IsSpecificLootTableCondition> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ResourceLocation.CODEC.listOf().fieldOf("loot_tables").forGetter(IsSpecificLootTableCondition::lootTables)
    ).apply(inst, IsSpecificLootTableCondition::new));

    @Override
    public LootItemConditionType getType() {
        return BuiltInRegistries.LOOT_CONDITION_TYPE.get(ID);
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this.lootTables().contains(lootContext.getQueriedLootTableId());
    }

    public static Builder isSpecificLootTableCondition(List<ResourceLocation> lootTableIds) {
        return () -> new IsSpecificLootTableCondition(lootTableIds);
    }
}
