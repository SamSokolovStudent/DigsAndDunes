package net.soko.digs_and_dunes.common.worldgen.feature.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.soko.digs_and_dunes.common.block.custom.DateBlock;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.core.registry.ModDecoratorPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class DateDecorator extends TreeDecorator {

    public static final Codec<DateDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(DateDecorator::new, (dateDecorator) -> {
        return dateDecorator.probability;
    }).codec();

    private final float probability;

    public DateDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return ModDecoratorPlacerType.DATE_DECORATOR_PLACER.get();
    }

    @Override
    public void place(TreeDecorator.Context context) {
        RandomSource randomSource = context.random();
        List<BlockPos> logs = context.logs();

        // Find the maximum Y-coordinate among all logs.
        int maxY = logs.stream().mapToInt(BlockPos::getY).max().orElseThrow();

        // Iterate through all logs and select those that are in the top 2 Y levels.
        for (BlockPos logPos : logs) {
            int logY = logPos.getY();

            // If the log is within the top 2 Y levels, replace it with a husk.
            if (logY >= maxY - 1) {
                context.setBlock(logPos, ModBlocks.PALM_HUSK.get().defaultBlockState());
            }

            // If the log is at the very top, place cocoa pods around it
            if (logY == maxY) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    Direction oppositeDirection = direction.getOpposite();
                    BlockPos potentialCocoaPos = logPos.offset(oppositeDirection.getStepX(), 0, oppositeDirection.getStepZ());

                    if (probability < randomSource.nextFloat()) {
                        continue;
                    }

                    if (context.isAir(potentialCocoaPos)) {
                        context.setBlock(potentialCocoaPos,
                                ModBlocks.DATE.get().defaultBlockState()
                                        .setValue(DateBlock.AGE, randomSource.nextInt(3))
                                        .setValue(DateBlock.FACING, direction)
                        );
                    }
                }
            }
        }
    }
}