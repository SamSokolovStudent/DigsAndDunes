package net.soko.digs_and_dunes.mixins;

import com.ibm.icu.impl.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.soko.digs_and_dunes.core.registry.ModBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin {


    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    public void digsanddunes$onTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (world.isEmptyBlock(pos.below())) {
            spawnLandslideBlock(state, world, pos);
            ci.cancel();
            return;
        }
        if (!isLandslideBlock(state)) {
            return;
        }
        if (!world.isClientSide()) {
            final Pair<BlockPos, Direction> landslideInfo = getLandslidePos(world, pos, state);
            if (landslideInfo != null) {
                final BlockPos fallPos = landslideInfo.first;
                final Direction fallDirection = landslideInfo.second;
                if (isAirOrEmptyFluid(world.getBlockState(fallPos))) {
                    world.removeBlock(pos, false);
                    world.destroyBlock(fallPos, true);
                    world.setBlock(fallPos, state, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
                    world.playSound(null, pos, SoundEvents.ROOTED_DIRT_FALL, SoundSource.BLOCKS, 0.4f, 0.8f);
                    spawnLandslideBlock(state, world, fallPos);
                    ci.cancel();
                }
            }
        }
    }

    private static void spawnLandslideBlock(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        for (BlockState blockState = pState; isLandslideBlock(blockState); blockState = pLevel.getBlockState(pPos)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(pLevel, pPos.immutable(), blockState);
            fallingBlockEntity.setHurtsEntities(2, 40);
            fallingBlockEntity.dropItem = true;
            pLevel.addFreshEntity(fallingBlockEntity);
        }
    }

    private static boolean isLandslideBlock(BlockState blockState) {
        return blockState.is(ModBlockTags.LANDSLIDE);
    }

    @Nullable
    private static Pair<BlockPos, Direction> getLandslidePos(Level level, BlockPos pos, BlockState fallingState) {
        if (!isSupportedOnSide(level, pos, Direction.UP)) {
            int supportedDirections = 0;
            List<Pair<BlockPos, Direction>> possibleDirections = new ArrayList<>();
            for (Direction side : Direction.Plane.HORIZONTAL) {
                if (isSupportedOnSide(level, pos, side)) {
                    supportedDirections++;
                    if (supportedDirections >= 2) {
                        return null;
                    }
                } else {
                    final BlockPos posSide = pos.relative(side), posSideBelow = posSide.below();
                    if (canFallThrough(level, posSide, side) && canFallThrough(level, posSideBelow, Direction.DOWN)) {
                        possibleDirections.add(Pair.of(posSide, side));
                    }
                }
            }

            if (!possibleDirections.isEmpty()) {
                if (supportedDirections == 1 && level.getRandom().nextInt(100) > 75) {
                    return null;
                }
                return possibleDirections.get(level.getRandom().nextInt(possibleDirections.size()));
            }
        }
        return null;
    }

    private static boolean canFallThrough(BlockGetter level, BlockPos pos, Direction fallingDirection) {
        BlockState state = level.getBlockState(pos);
        return !state.isFaceSturdy(level, pos, fallingDirection.getOpposite()) && state.canBeReplaced();
    }

    private static boolean isSupportedOnSide(BlockGetter world, BlockPos pos, Direction side) {
        BlockPos sidePos = pos.relative(side);
        BlockState sideState = world.getBlockState(sidePos);
        return sideState.isFaceSturdy(world, sidePos, side.getOpposite());
    }

    private static boolean isAirOrEmptyFluid(BlockState blockState) {
        return blockState.canBeReplaced();
    }
}

