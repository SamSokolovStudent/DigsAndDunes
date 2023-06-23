package net.soko.digs_and_dunes.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.common.worldgen.feature.generator.ArchingTrunkPlacer;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModTrunkPlacerType {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, DigsAndDunes.MOD_ID);


    public static final RegistryObject<TrunkPlacerType<?>> ARCHING_TRUNK_PLACER_TYPE =
            TRUNK_PLACERS.register("arching_trunk_placer_type",
                    () -> new TrunkPlacerType<>(ArchingTrunkPlacer.CODEC));


    public static void register(IEventBus eventbus) {
        TRUNK_PLACERS.register(eventbus);
    }

}