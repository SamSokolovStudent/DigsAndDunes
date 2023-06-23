package net.soko.digs_and_dunes.mixins;

import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(FogType.class)
public class FogTypeMixin {
    @Shadow
    @Final
    @Mutable
    private static FogType[] $VALUES;

    private static final FogType QUICKSAND = fogTypeExpansion$addVariant("QUICKSAND");

    @Invoker("<init>")
    public static FogType fogTypeExpansion$invokeInit(String internalName, int internalId) {
        throw new AssertionError();
    }

    private static FogType fogTypeExpansion$addVariant(String internalName) {
        ArrayList<FogType> variants = new ArrayList<FogType>(Arrays.asList(FogTypeMixin.$VALUES));
        FogType fogType = fogTypeExpansion$invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1);
        variants.add(fogType);
        FogTypeMixin.$VALUES = variants.toArray(new FogType[0]);
        return fogType;
    }
}

