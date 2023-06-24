package net.soko.digs_and_dunes.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.soko.digs_and_dunes.core.registry.ModItems;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;

import static net.minecraft.util.Mth.randomBetween;
import static net.minecraft.world.level.block.PowderSnowBlock.canEntityWalkOnPowderSnow;

public class Quicksand extends Block implements BucketPickup {
    private static final float HORIZONTAL_PARTICLE_MOMENTUM_FACTOR = 0.083333336F;
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.9F, 1.0D);


    private final int dustColor;

    public Quicksand(Properties properties, int dustColor) {
        super(properties);
        this.dustColor = dustColor;
    }

    public int getDustColor() {
        return this.dustColor;
    }

    @Override
    public void entityInside(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos position, @NotNull Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            double d = entity.getX() - entity.xOld;
            double e = entity.getZ() - entity.zOld;
            float f = (float)(d * d + e * e);
            double stuckY = Math.min(Math.log(10*Mth.sqrt(f) +1) * 0.75 + 0.025, 1.5);
            double stuckXZ;
            if (entity.onGround()) {
                stuckXZ = 0.12f;
            } else {
                stuckXZ = 0.9f;
            }
            entity.makeStuckInBlock(blockState, new Vec3(stuckXZ, stuckY, stuckXZ));
            if (world.isClientSide) {
                RandomSource random = world.getRandom();
                boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (flag && random.nextBoolean()) {
                    Color color = new Color(this.getDustColor());
                    Vec3 dustColorVec = new Vec3(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
                    world.addParticle(new DustParticleOptions(dustColorVec.toVector3f(), 1),
                            entity.getX() + 0.2, position.getY() + 1, entity.getZ() + 0.2,
                            randomBetween(random, -1.0F, 1.0F) * HORIZONTAL_PARTICLE_MOMENTUM_FACTOR,
                            0.05F,
                            randomBetween(random, -1.0F, 1.0F) * HORIZONTAL_PARTICLE_MOMENTUM_FACTOR);
                }
            }
        }
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }

    @Override
    public void fallOn(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Entity entity, float fallDistance) {
        if (!(fallDistance < 4.0D) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.Fallsounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = fallDistance < 7.0D ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos position, @NotNull CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_COLLISION_SHAPE;
                }

                boolean flag = entity instanceof FallingBlockEntity;
                if (flag || canEntityWalkOnPowderSnow(entity) && context.isAbove(Shapes.block(), position, false) && !context.isDescending()) {
                    return super.getCollisionShape(state, world, position, context);
                }
            }
        }

        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos position, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    public static boolean canEntityWalkOnQuicksand(Entity entity) {
        if (entity.getType().is(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) { //todo: Replace this tag with a new one for entities able to walk on quicksand.
            return true;
        } else {
            return entity instanceof LivingEntity && ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).canWalkOnPowderedSnow((LivingEntity) entity); //todo: Here, replace canWalkOnPowderedSnow with equivalent for quicksand.
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound(BlockState state) {
        return BucketPickup.super.getPickupSound(state);
    }

    public @NotNull ItemStack pickupBlock(LevelAccessor p_154281_, @NotNull BlockPos p_154282_, @NotNull BlockState p_154283_) {
        p_154281_.setBlock(p_154282_, Blocks.AIR.defaultBlockState(), 11);
        if (!p_154281_.isClientSide()) {
            p_154281_.levelEvent(2001, p_154282_, Block.getId(p_154283_));
        }

        return new ItemStack(ModItems.QUICKSAND_BUCKET.get());
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos position, @NotNull PathComputationType type) {
        return true;
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pDirection);
    }
}
