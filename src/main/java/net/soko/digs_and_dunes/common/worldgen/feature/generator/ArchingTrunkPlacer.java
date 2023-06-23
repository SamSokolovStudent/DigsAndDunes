package net.soko.digs_and_dunes.common.worldgen.feature.generator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.phys.Vec3;
import net.soko.digs_and_dunes.core.registry.ModTrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ArchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<ArchingTrunkPlacer> CODEC = RecordCodecBuilder.create((archTrunkInstance) -> {
        return trunkPlacerParts(archTrunkInstance)
                .and(IntProvider.codec(1, 64).fieldOf("bend_length").forGetter(archingTrunk -> archingTrunk.bendLength))
                .apply(archTrunkInstance, ArchingTrunkPlacer::new);
    });

    private final IntProvider bendLength;

    public ArchingTrunkPlacer(int baseHeight, int randMinHeight, int randMaxHeight, IntProvider bendLength) {
        super(baseHeight, randMinHeight, randMaxHeight);
        this.bendLength = bendLength;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerType.ARCHING_TRUNK_PLACER_TYPE.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int treeHeight, BlockPos pos, TreeConfiguration config) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
        int baseHeight = treeHeight - 1;
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();

        Vec3 startDirection = new Vec3((random.nextDouble() - 0.5) * 2, 1.5, (random.nextDouble() - 0.5) * 2);
        Vec3 endDirection = new Vec3((random.nextDouble() - 0.5) * 2, 1.5, (random.nextDouble() - 0.5) * 2);


        // Normalize the direction vectors so their magnitudes are 1
        startDirection = startDirection.normalize();
        endDirection = endDirection.normalize();

        // Scale the directions based on the bend length
        int bend = bendLength.sample(random);
        startDirection = startDirection.scale(bend);
        endDirection = endDirection.scale(bend);

        // Use DDA algorithm to draw a smooth path from the start to the end
        for (int i = 0; i <= baseHeight; i++) {
            double t = (double) i / baseHeight;
            double easingT = polyInOut(t, 3);

            // Interpolate between the start and end directions using the easing function
            Vec3 direction = startDirection.scale(1 - easingT).add(endDirection.scale(easingT));

            // Calculate the next position and round to the nearest integer coordinates
            BlockPos nextPos = new BlockPos(
                    (int) (pos.getX() + Math.round(direction.x * t)),
                    pos.getY() + i,
                    (int) (pos.getZ() + Math.round(direction.z * t))
            );

            // Add the position to the set
            dda(mutableBlockPos, nextPos, trunkPos -> {
                if (TreeFeature.validTreePos(level, trunkPos)) {
                    this.placeLog(level, blockSetter, random, trunkPos, config);
                }
            });

            mutableBlockPos.set(nextPos);
        }

        // Add foliage at the top of the trunk
        mutableBlockPos.move(0, 1, 0);
        list.add(new FoliagePlacer.FoliageAttachment(mutableBlockPos.immutable(), 0, false));

        return list;
    }

    public double polyInOut(double t, int e) {
        t *= 2;
        return (t <= 1 ? Math.pow(t, e) : 2 - Math.pow(2 - t, e)) / 2;
    }

    public static void dda(BlockPos from, BlockPos to, Consumer<BlockPos> consumer) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        consumer.accept(mutable.set(from.getX(), from.getY(), from.getZ()));

        if (to.equals(from)) {
            return;
        }

        Vec3 ray = Vec3.atLowerCornerOf(to.subtract(from)).normalize();

        //which box of the map we're in
        int mapX = from.getX();
        int mapY = from.getY();
        int mapZ = from.getZ();

        //length of ray from one x, y or z-side to next x, y or z-side
        double deltaDistX = Math.abs(1 / ray.x());
        double deltaDistY = Math.abs(1 / ray.y());
        double deltaDistZ = Math.abs(1 / ray.z());

        //what direction to step (either +1 or -1)
        int stepX = ray.x() < 0 ? -1 : 1;
        int stepY = ray.y() < 0 ? -1 : 1;
        int stepZ = ray.z() < 0 ? -1 : 1;

        //length of ray from current position to next x, y or z-side
        double sideDistX = 0.5 * deltaDistX;
        double sideDistY = 0.5 * deltaDistY;
        double sideDistZ = 0.5 * deltaDistZ;

        int limit = 0;

        //perform DDA
        while (true) {
            //jump to next map square, either in x, y or z-direction
            if (sideDistZ < sideDistX && sideDistZ < sideDistY) {
                sideDistZ += deltaDistZ;
                mapZ += stepZ;
            } else if (sideDistX < sideDistY) {
                sideDistX += deltaDistX;
                mapX += stepX;
            } else {
                sideDistY += deltaDistY;
                mapY += stepY;
            }

            consumer.accept(mutable.set(mapX, mapY, mapZ));

            if (mapX == to.getX() && mapY == to.getY() && mapZ == to.getZ()) return;
        }
    }


}
