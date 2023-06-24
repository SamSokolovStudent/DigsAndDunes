package net.soko.digs_and_dunes.common.worldgen.feature.generator;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.soko.digs_and_dunes.core.registry.ModConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

public class PalmTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222910_, boolean p_222911_) {
        return ModConfiguredFeatures.PALM_KEY;
    }
}
