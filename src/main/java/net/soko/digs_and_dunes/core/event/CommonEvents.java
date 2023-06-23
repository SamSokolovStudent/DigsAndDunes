package net.soko.digs_and_dunes.core.event;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class CommonEvents {
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getItemStack();
        BlockPos pos = event.getPos();
        RandomSource random = event.getLevel().random;
    }
}
