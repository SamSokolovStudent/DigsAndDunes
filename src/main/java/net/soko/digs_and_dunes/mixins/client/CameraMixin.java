package net.soko.digs_and_dunes.mixins.client;

import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(method = "getFluidInCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void digsanddunes$getFluidInCamera(CallbackInfoReturnable<FogType> cir, FluidState fluidstate, Camera.NearPlane camera$nearplane, Iterator var3, Vec3 vec3, Vec3 vec31, BlockPos blockpos, FluidState fluidstate1, BlockState blockstate) {
        if (blockstate.is(ModBlocks.QUICKSAND.get())) {
            cir.setReturnValue(FogType.valueOf("QUICKSAND"));
        }
    }
}
