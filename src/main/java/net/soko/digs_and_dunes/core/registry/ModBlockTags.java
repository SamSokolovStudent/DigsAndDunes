package net.soko.digs_and_dunes.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModBlockTags {
    public static TagKey<Block> LANDSLIDE = BlockTags.create(new ResourceLocation(DigsAndDunes.MOD_ID, "landslide"));
}
