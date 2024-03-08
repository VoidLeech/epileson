package net.merchantpug.epileson.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.epileson.Epileson;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.loot.CanToolPerformAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

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

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<IsSpecificLootTableCondition> {
        public void serialize(JsonObject json, IsSpecificLootTableCondition itemCondition, @NotNull JsonSerializationContext context) {
            JsonElement codecJson = CODEC.encodeStart(JsonOps.INSTANCE, itemCondition).getOrThrow(false, Epileson.LOG::error);
            if (codecJson instanceof JsonObject object) {
                for (Map.Entry<String, JsonElement> entry : object.asMap().entrySet()) {
                    json.add(entry.getKey(), entry.getValue());
                }
            }
        }

        @NotNull
        public IsSpecificLootTableCondition deserialize(JsonObject json, @NotNull JsonDeserializationContext context) {
            return CODEC.decode(JsonOps.INSTANCE, json).getOrThrow(false, Epileson.LOG::error).getFirst();
        }
    }
}
