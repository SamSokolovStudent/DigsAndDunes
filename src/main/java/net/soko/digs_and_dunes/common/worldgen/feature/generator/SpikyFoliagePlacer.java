package net.soko.digs_and_dunes.common.worldgen.feature.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;
import net.soko.digs_and_dunes.core.registry.ModFoliagePlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;


public class SpikyFoliagePlacer extends FoliagePlacer {

    public static final Codec<SpikyFoliagePlacer> CODEC = RecordCodecBuilder.create((placer) -> {
        return foliagePlacerParts(placer).apply(placer, SpikyFoliagePlacer::new);
    });

    public SpikyFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    protected @NotNull FoliagePlacerType<?> type() {
        return ModFoliagePlacerType.SPIKY_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int treeHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        MutableBlockPos mutableBlockPos = attachment.pos().above(offset).mutable();
        double phi = (1 + Math.sqrt(5)) / 2; // golden ratio

        // Compute larger radius and frond size for taller trees
        int scaledRadius = (int) (radius * treeHeight * 0.1);
        double frondSizeFactor = 1.3; // adjust this value to increase or decrease the frond size

        // Compute random rotation angle
        double theta = randomSource.nextDouble() * 2 * Math.PI;

        Vec3[] vertices = new Vec3[]{
                rotate(new Vec3(-1, phi, 0), theta),
                rotate(new Vec3(1, phi, 0), theta),
                rotate(new Vec3(-1, -phi, 0), theta),
                rotate(new Vec3(1, -phi, 0), theta),
                rotate(new Vec3(0, -1, phi), theta),
                rotate(new Vec3(0, 1, phi), theta),
                rotate(new Vec3(0, -1, -phi), theta),
                rotate(new Vec3(0, 1, -phi), theta),
                rotate(new Vec3(phi, 0, -1), theta),
                rotate(new Vec3(phi, 0, 1), theta),
                rotate(new Vec3(-phi, 0, -1), theta),
                rotate(new Vec3(-phi, 0, 1), theta)
        };

        for (Vec3 vertex : vertices) {
            Vec3 direction = vertex.normalize().scale(scaledRadius);
            BlockPos endPos = new BlockPos(
                    (int) (mutableBlockPos.getX() + direction.x * frondSizeFactor),
                    (int) (mutableBlockPos.getY() + direction.y * frondSizeFactor),
                    (int) (mutableBlockPos.getZ() + direction.z * frondSizeFactor)
            );

            // Use DDA to iterate over blocks from start to end position
            dda(levelSimulatedReader, mutableBlockPos.immutable(), endPos, pos -> {
                tryPlaceLeaf(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, pos);
            });

            mutableBlockPos.set(attachment.pos().above(offset));
        }

        this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, mutableBlockPos, 1, 1, false);
    }

    public static void dda(LevelSimulatedReader levelSimulatedReader, BlockPos from, BlockPos to, Consumer<BlockPos> consumer) {
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

            BlockPos pos = mutable.set(mapX, mapY, mapZ);
            if (levelSimulatedReader.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS))) {
                break;
            }

            consumer.accept(mutable.set(mapX, mapY, mapZ));

            if (mapX == to.getX() && mapY == to.getY() && mapZ == to.getZ()) return;
        }
    }

    private Vec3 rotate(Vec3 vec, double theta) {
        double x = vec.x * Math.cos(theta) + vec.z * Math.sin(theta);
        double z = -vec.x * Math.sin(theta) + vec.z * Math.cos(theta);
        return new Vec3(x, vec.y, z);
    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}
