package net.soko.digs_and_dunes.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.common.worldgen.feature.decorator.DateDecorator;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModDecoratorPlacerType {
    public static final DeferredRegister<TreeDecoratorType<?>> DECORATOR_PLACERS =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, DigsAndDunes.MOD_ID);


    public static final RegistryObject<TreeDecoratorType<?>> DATE_DECORATOR_PLACER =
            DECORATOR_PLACERS.register("date_decorator_placer_type",
                    () -> new TreeDecoratorType<>(DateDecorator.CODEC));

    public static void register(IEventBus eventbus) {
        DECORATOR_PLACERS.register(eventbus);
    }
}
