package net.soko.digs_and_dunes.core;

import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.soko.digs_and_dunes.core.event.ClientEvents;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.core.registry.ModItems;

public class DigsAndDunesClient {
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(ClientEvents.class);
        modEventBus.addListener(DigsAndDunesClient::registerColorHandlersBlockEvent);
        modEventBus.addListener(DigsAndDunesClient::registerColorHandlersItemEvent);
        MinecraftForge.EVENT_BUS.addListener(ClientEvents::onRenderFog);
    }

    public static void registerColorHandlersBlockEvent(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
            return FoliageColor.get(0.825d, 0.225d);
        }, ModBlocks.PALM_LEAVES.get());
    }

    public static void registerColorHandlersItemEvent(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            return FoliageColor.get(0.825d, 0.225d);
        }, ModItems.PALM_LEAVES.get());
    }
}
