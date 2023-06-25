package net.soko.digs_and_dunes.core;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.soko.digs_and_dunes.core.event.ClientEvents;
import net.soko.digs_and_dunes.core.registry.ModBlocks;

public class DigsAndDunesClient {
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(ClientEvents.class);
        modEventBus.addListener(DigsAndDunesClient::registerColorHandlersEvent);
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::onRenderFog);
    }

    public static void registerColorHandlersEvent(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
            return FoliageColor.get(0.742d, 0.6842d);
        }, ModBlocks.PALM_LEAVES.get());
    }
}
