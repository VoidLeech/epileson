package net.merchantpug.epileson.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class EpilesonArmorMaterials {
    public static final ArmorMaterial EPILESON = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            int multiplier = switch (type) {
                case HELMET -> 11;
                case CHESTPLATE -> 16;
                case LEGGINGS -> 15;
                case BOOTS -> 13;
            };
            return multiplier * 18;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 2;
                case CHESTPLATE -> 5;
                case LEGGINGS -> 4;
                case BOOTS -> 1;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return 18;
        }

        @Override
        public SoundEvent getEquipSound() {
            return EpilesonSoundEvents.ARMOR_EQUIP_EPILESON;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(EpilesonItems.EPILESON_SHEET);
        }

        @Override
        public String getName() {
            return "epileson";
        }

        @Override
        public float getToughness() {
            return 2.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };
}
