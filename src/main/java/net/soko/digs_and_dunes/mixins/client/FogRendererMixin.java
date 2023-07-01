package net.soko.digs_and_dunes.mixins.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.renderer.FogRenderer")
@Unique
public class FogRendererMixin {


    @Shadow
    private static float fogRed;

    @Shadow
    private static float fogBlue;

    @Shadow
    private static float fogGreen;

    @Shadow
    private static long biomeChangedTime;

    @Inject(method = "setupColor", at = @At(value = "HEAD"), cancellable = true)
    private static void digsanddunes$onSetupColor(Camera pActiveRenderInfo, float pPartialTicks, ClientLevel pLevel, int pRenderDistanceChunks, float pBossColorModifier, CallbackInfo ci) {
        FogType fogtype = pActiveRenderInfo.getFluidInCamera();
        if (fogtype == FogType.valueOf("QUICKSAND")) {
            fogRed = 0.835f;
            fogGreen = 0.769f;
            fogBlue = 0.588f;
            biomeChangedTime = -1L;
            RenderSystem.clearColor(fogRed, fogGreen, fogBlue, 0.0F);
            ci.cancel();
        }
    }
}
