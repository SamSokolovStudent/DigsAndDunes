package net.soko.digs_and_dunes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.OptionalInt;

public class PalmFrondBlock extends LeavesBlock {
    public static final int DECAY_DISTANCE = 13;
    public static final IntegerProperty PALM_FROND_DISTANCE = IntegerProperty.create("distance2", 1, 13);


    public PalmFrondBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected boolean decaying(BlockState pState) {
        return !pState.getValue(PERSISTENT) && pState.getValue(PALM_FROND_DISTANCE) > DECAY_DISTANCE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(PALM_FROND_DISTANCE) == DECAY_DISTANCE && !pState.getValue(PERSISTENT);
    }

    private static BlockState updateDistance(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        int i = DECAY_DISTANCE;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pPos, direction);
            i = Math.min(i, getDistanceAt(pLevel.getBlockState(blockpos$mutableblockpos)) + 1);
            if (i == 1) {
                break;
            }
        }

        return pState.setValue(PALM_FROND_DISTANCE, Integer.valueOf(i));
    }

    private static int getDistanceAt(BlockState pNeighbor) {
        return getOptionalDistanceAt(pNeighbor).orElse(DECAY_DISTANCE);
    }

    public static OptionalInt getOptionalDistanceAt(BlockState blockState) {
        if (blockState.is(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else {
            return blockState.hasProperty(PALM_FROND_DISTANCE) ? OptionalInt.of(blockState.getValue(PALM_FROND_DISTANCE)) : OptionalInt.empty();
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(PALM_FROND_DISTANCE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        return updateDistance(blockstate, pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
