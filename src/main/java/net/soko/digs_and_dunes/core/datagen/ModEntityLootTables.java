package net.soko.digs_and_dunes.core.datagen;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.core.registry.ModEntities;
import net.soko.digs_and_dunes.core.registry.ModLootTables;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    public ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(EntityType.SHEEP, ModLootTables.SHEEP_MAROON, createSheepTable(ModBlocks.MAROON_WOOL.get()));
        add(EntityType.SHEEP, ModLootTables.SHEEP_OCHRE, createSheepTable(ModBlocks.OCHRE_WOOL.get()));
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
