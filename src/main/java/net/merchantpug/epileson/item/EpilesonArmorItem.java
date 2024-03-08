package net.merchantpug.epileson.item;

import net.merchantpug.epileson.Epileson;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class EpilesonArmorItem extends ArmorItem {
    public EpilesonArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", Epileson.MOD_ID, "epileson", (slot.equals(EquipmentSlot.LEGS) ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
    }

}
