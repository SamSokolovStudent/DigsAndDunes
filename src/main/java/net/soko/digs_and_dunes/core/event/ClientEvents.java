package net.soko.digs_and_dunes.core.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.soko.digs_and_dunes.client.screen.PotteryTableScreen;
import net.soko.digs_and_dunes.core.registry.ModMenuTypes;

public class ClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.POTTERY_TABLE.get(), PotteryTableScreen::new);
    }

    public static void onRenderFog(ViewportEvent.RenderFog event) {
        float start;
        float end;
        FogShape shape = FogShape.SPHERE;
        Entity entity = event.getCamera().getEntity();

        if (event.getCamera().getFluidInCamera() == FogType.valueOf("QUICKSAND")) {
            if (entity.isSpectator()) {
                start = -8.0F;
                end = event.getFarPlaneDistance() * 0.5F;
            } else {
                start = 0.0F;
                end = 1.4F;
            }
            RenderSystem.setShaderFogStart(start);
            RenderSystem.setShaderFogEnd(end);
            RenderSystem.setShaderFogShape(shape);
        }
    }
}
