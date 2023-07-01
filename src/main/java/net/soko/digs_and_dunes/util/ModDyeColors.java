package net.soko.digs_and_dunes.util;

import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.List;

public class ModDyeColors {

    public static List<DyeColor> DYES = new ArrayList<>();

    public static DyeColor digsAndDunes$MAROON;
    public static DyeColor digsAndDunes$OCHRE;

    public static void init() {
        digsAndDunes$MAROON = DyeColor.byName("digs_and_dunes_maroon", DyeColor.WHITE);
        digsAndDunes$OCHRE = DyeColor.byName("digs_and_dunes_ochre", DyeColor.WHITE);

        DYES.add(digsAndDunes$MAROON);
        DYES.add(digsAndDunes$OCHRE);
    }
}
