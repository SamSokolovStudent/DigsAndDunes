package net.soko.digs_and_dunes.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.common.worldgen.feature.generator.SpikyFoliagePlacer;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModFoliagePlacerType {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, DigsAndDunes.MOD_ID);


    public static final RegistryObject<FoliagePlacerType<?>> SPIKY_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("arching_trunk_placer_type",
                    () -> new FoliagePlacerType<>(SpikyFoliagePlacer.CODEC));


    public static void register(IEventBus eventbus) {
        FOLIAGE_PLACERS.register(eventbus);
    }
}
