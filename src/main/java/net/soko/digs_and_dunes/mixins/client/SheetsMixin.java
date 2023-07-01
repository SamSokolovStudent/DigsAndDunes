package net.soko.digs_and_dunes.mixins.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.soko.digs_and_dunes.core.DigsAndDunes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ImmutableList.*;
import static net.minecraft.client.renderer.Sheets.SHULKER_SHEET;


@Mixin(Sheets.class)
public class SheetsMixin {

    @Mutable
    @Shadow
    @Final
    private static List<Material> SHULKER_TEXTURE_LOCATION;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addShulkerBoxTypes(CallbackInfo ci) {
        var modified = new ArrayList<>(SHULKER_TEXTURE_LOCATION);

        Material maroonMaterial = new Material(SHULKER_SHEET, new ResourceLocation(DigsAndDunes.MOD_ID, "entity/shulker/shulker_maroon"));
        Material ochreMaterial = new Material(SHULKER_SHEET, new ResourceLocation(DigsAndDunes.MOD_ID, "entity/shulker/shulker_ochre"));

        modified.add(maroonMaterial);
        modified.add(ochreMaterial);

        SHULKER_TEXTURE_LOCATION = copyOf(modified);
    }

}

