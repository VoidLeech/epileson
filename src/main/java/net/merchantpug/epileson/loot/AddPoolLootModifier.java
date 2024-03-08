package net.merchantpug.epileson.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.registry.EpilesonItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddPoolLootModifier extends LootModifier {
    public static final ResourceLocation ID = Epileson.asResource("add_epileson_upgrade_smithing_template");
    public static final Codec<AddPoolLootModifier> CODEC = RecordCodecBuilder.create(inst -> LootModifier.codecStart(inst).apply(inst, AddPoolLootModifier::new));
    private static final LootPool pool = LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(EmptyLootItem.emptyItem().setWeight(2))
            .add(LootItem.lootTableItem(EpilesonItems.EPILESON_UPGRADE_SMITHING_TEMPLATE.get()).setWeight(1))
            .build();

    protected AddPoolLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext lootContext) {
        pool.addRandomItems(objectArrayList::add, lootContext);
        return objectArrayList;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
