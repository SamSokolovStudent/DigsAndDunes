package net.soko.digs_and_dunes.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.soko.digs_and_dunes.core.event.ClientEvents;

public class DigsAndDunesClient {
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(ClientEvents.class);
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::onRenderFog);
    }
}
