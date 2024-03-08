package net.merchantpug.epileson.mixin;

import net.merchantpug.epileson.Epileson;
import net.merchantpug.epileson.registry.EpilesonTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract Iterable<ItemStack> getArmorSlots();

    @ModifyVariable(method = "hurt", at = @At("HEAD"), argsOnly = true)
    private float epileson$reduceVelocityBasedDamage(float original, DamageSource damageSource) {
        if (damageSource.is(EpilesonTags.DamageTypeTags.IS_KINETIC)) {
            return Epileson.reduceKineticDamageWithArmor(this.getArmorSlots(), original);
        }
        return original;
    }
}
