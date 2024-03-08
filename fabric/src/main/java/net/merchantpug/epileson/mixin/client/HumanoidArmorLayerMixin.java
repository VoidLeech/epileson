package net.merchantpug.epileson.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.merchantpug.epileson.registry.EpilesonArmorMaterials;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @ModifyArg(method = "getArmorLocation", at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    private <K> K epileson$returnEpilesonId(K original, @Local(argsOnly = true) ArmorItem item) {
        if (item.getMaterial() == EpilesonArmorMaterials.EPILESON) {
            return (K) ("epileson:" + original);
        }
        return original;
    }
}
