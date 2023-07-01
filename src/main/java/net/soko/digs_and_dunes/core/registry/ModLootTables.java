package net.soko.digs_and_dunes.core.registry;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.soko.digs_and_dunes.core.DigsAndDunes;

import java.util.Collections;
import java.util.Set;

public class ModLootTables {
    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
    public static final ResourceLocation SHEEP_MAROON = register("entities/sheep/white");
    public static final ResourceLocation SHEEP_OCHRE = register("entities/sheep/orange");

    private static ResourceLocation register(String pId) {
        return register(new ResourceLocation(DigsAndDunes.MOD_ID, pId));
    }

    private static ResourceLocation register(ResourceLocation pId) {
        if (LOCATIONS.add(pId)) {
            return pId;
        } else {
            throw new IllegalArgumentException(pId + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
