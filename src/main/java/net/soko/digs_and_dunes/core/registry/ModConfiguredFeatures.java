package net.soko.digs_and_dunes.core.registry;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.soko.digs_and_dunes.common.worldgen.feature.decorator.DateDecorator;
import net.soko.digs_and_dunes.common.worldgen.feature.placer.ArchingTrunkPlacer;
import net.soko.digs_and_dunes.common.worldgen.feature.placer.SpikyFoliagePlacer;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_KEY = registerKey("palm");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        register(context, PALM_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PALM_WOOD.get()),
                new ArchingTrunkPlacer(7, 3, 5, UniformInt.of(6, 9)),
                BlockStateProvider.simple(ModBlocks.PALM_LEAVES.get()),
                new SpikyFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2)).decorators(ImmutableList.of(new DateDecorator(0.4f))).build());
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DigsAndDunes.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
