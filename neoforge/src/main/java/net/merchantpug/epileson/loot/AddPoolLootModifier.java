package net.merchantpug.epileson.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.merchantpug.epileson.Epileson;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddPoolLootModifier extends LootModifier {
    public static final ResourceLocation ID = Epileson.asResource("add_pools");
    public static final Codec<AddPoolLootModifier> CODEC = RecordCodecBuilder.create(inst ->
            LootModifier.codecStart(inst).and(
                    LootPool.CODEC.listOf().fieldOf("entries").forGetter(AddPoolLootModifier::pools)
            ).apply(inst, AddPoolLootModifier::new));

    private final List<LootPool> pools;

    protected AddPoolLootModifier(LootItemCondition[] conditionsIn, List<LootPool> pool) {
        super(conditionsIn);
        this.pools = pool;
    }

    public List<LootPool> pools() {
        return this.pools;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        pools().forEach(pool -> pool.addRandomItems(objectArrayList::add, lootContext));
        return objectArrayList;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
