package net.merchantpug.epileson;

import net.merchantpug.epileson.registry.EpilesonTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Epileson {

    public static final String MOD_ID = "epileson";
    public static final String MOD_NAME = "Epileson";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {

    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static float reduceKineticDamageWithArmor(Iterable<ItemStack> armorItems, float damageValue) {
        float multiplier = 1.0F;
        for (ItemStack armorStack : armorItems) {
            if (armorStack.is(EpilesonTags.ItemTags.KINETIC_DAMAGE_RESISTANT)) {
                multiplier -= 0.05F;
            }
        }
        return damageValue * multiplier;
    }

}