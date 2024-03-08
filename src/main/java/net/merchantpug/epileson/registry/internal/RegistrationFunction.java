package net.merchantpug.epileson.registry.internal;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface RegistrationFunction<T> {
    void register(Registry<T> registry, ResourceLocation id, T value);
}
