package net.soko.digs_and_dunes.mixins.enummixins;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

@Mixin(DyeColor.class)
public abstract class DyeColorMixin {
    @Shadow
    @Final
    @Mutable
    private static DyeColor[] $VALUES;
    private static final DyeColor digsAndDunes$MAROON = digsAndDunes$addDye("digsAndDunes$MAROON", "digs_and_dunes_maroon", 9175354 , MapColor.COLOR_PURPLE, 9175354, 9175354);
    private static final DyeColor digsAndDunes$OCHRE = digsAndDunes$addDye("digsAndDunes$OCHRE", "digs_and_dunes_ochre", 13602317, MapColor.COLOR_YELLOW, 13602317, 13602317);

    @Shadow
    @Final
    @Mutable
    private static final IntFunction<DyeColor> BY_ID = ByIdMap.continuous(DyeColor::getId, $VALUES, ByIdMap.OutOfBoundsStrategy.ZERO);

    @Shadow
    @Final
    @Mutable
    private static final Int2ObjectOpenHashMap<DyeColor> BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap<>(Arrays.stream($VALUES).collect(Collectors.toMap(DyeColor::getFireworkColor, (dyeColor) -> dyeColor)));

    @Shadow @Final private int id;

    @Invoker("<init>")
    public static DyeColor invokeInit(String internalName, int internalId, int id, String p_41047_, int p_41048_, MapColor p_41049_, int p_41050_, int p_41051_) {
        throw new AssertionError();
    }

    private static DyeColor digsAndDunes$addDye(String internalName, String p_41047_, int p_41048_, MapColor p_41049_, int p_41050_, int p_41051_) {
        ArrayList<DyeColor> variants = new ArrayList<DyeColor>(Arrays.asList($VALUES));
        DyeColor color = invokeInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, variants.get(variants.size() - 1).ordinal() + 1, p_41047_, p_41048_, p_41049_, p_41050_, p_41051_);
        variants.add(color);
        $VALUES = variants.toArray(new DyeColor[0]);
        return color;
    }
}
