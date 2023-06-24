package net.soko.digs_and_dunes.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModItemTags {
    public static TagKey<Item> POTTERY_TABLE_INPUTS = ItemTags.create(new ResourceLocation(DigsAndDunes.MOD_ID, "pottery_table_inputs"));
}