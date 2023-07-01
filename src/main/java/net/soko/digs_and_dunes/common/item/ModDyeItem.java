package net.soko.digs_and_dunes.common.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModDyeItem extends DyeItem {
    private final Item parentItem;
    private static final Map<Item, List<Item>> itemIndexes = new HashMap<>();

    public ModDyeItem(DyeColor dyeItem, Properties properties, Item parentItem) {
        super(dyeItem, properties);
        this.parentItem = parentItem;
        if (!itemIndexes.keySet().contains(parentItem))
            itemIndexes.put(parentItem, new ArrayList<>());
        itemIndexes.get(parentItem).add(this);
    }



}
