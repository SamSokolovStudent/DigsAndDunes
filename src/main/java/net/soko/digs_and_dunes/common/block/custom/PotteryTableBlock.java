package net.soko.digs_and_dunes.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.soko.digs_and_dunes.common.menu.PotteryTableMenu;
import org.jetbrains.annotations.Nullable;

public class PotteryTableBlock extends Block {

    private static final Component CONTAINER_TITLE = Component.literal("Pottery Table");
    public PotteryTableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((containerId, pInventory, pPlayer) -> new PotteryTableMenu(containerId, pInventory, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
    }
}
