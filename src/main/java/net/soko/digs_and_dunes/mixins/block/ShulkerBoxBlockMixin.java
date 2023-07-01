package net.soko.digs_and_dunes.mixins.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.util.ModDyeColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "getBlockByColor", at = @At(value = "HEAD"), cancellable = true)
    private static void getCustomColorShulkerBox(DyeColor dyeColor, CallbackInfoReturnable<Block> cir) {
        if (dyeColor == null) {
            cir.setReturnValue(Blocks.SHULKER_BOX);
        } else {
            if (dyeColor == ModDyeColors.digsAndDunes$MAROON) {
                cir.setReturnValue(ModBlocks.MAROON_SHULKER_BOX.get());
            }
            if (dyeColor == ModDyeColors.digsAndDunes$OCHRE) {
                cir.setReturnValue(ModBlocks.OCHRE_SHULKER_BOX.get());
            }
        }
    }
}
