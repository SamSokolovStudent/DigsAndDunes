package net.soko.digs_and_dunes.core.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.common.block.custom.DateBlock;
import net.soko.digs_and_dunes.core.registry.ModBlocks;
import net.soko.digs_and_dunes.core.registry.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(ModBlocks.SUSPICIOUS_DIRT.get(), noDrop());
        add(ModBlocks.PETRIFIED_WOOD.get(), noDrop());
        add(ModBlocks.PETRIFIED_PLANKS.get(), noDrop());
        add(ModBlocks.QUICKSAND.get(), noDrop());


        dropSelf(ModBlocks.PALM_LOG.get());
        dropSelf(ModBlocks.PALM_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_PALM_LOG.get());
        dropSelf(ModBlocks.STRIPPED_PALM_WOOD.get());
        dropSelf(ModBlocks.PALM_HUSK.get());
        add(ModBlocks.DATE.get(), (block) -> {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DATE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DateBlock.AGE, 3)))))));
        });

        dropSelf(ModBlocks.PALM_PLANKS.get());
        dropSelf(ModBlocks.PALM_DOOR.get());
        dropSelf(ModBlocks.PALM_TRAPDOOR.get());
        dropSelf(ModBlocks.PALM_FENCE.get());
        dropSelf(ModBlocks.PALM_FENCE_GATE.get());
        dropSelf(ModBlocks.PALM_BUTTON.get());
        dropSelf(ModBlocks.PALM_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.PALM_SIGN.get());
        dropSelf(ModBlocks.PALM_WALL_SIGN.get());
        dropSelf(ModBlocks.PALM_HANGING_SIGN.get());
        dropSelf(ModBlocks.PALM_WALL_HANGING_SIGN.get());

        dropSelf(ModBlocks.POTTERY_TABLE.get());

        dropSelf(ModBlocks.PALM_SAPLING.get());
        dropPottedContents(ModBlocks.POTTED_PALM_SAPLING.get());

        dropSelf(ModBlocks.PALM_STAIRS.get());
        dropSelf(ModBlocks.PALM_SLAB.get());

        dropSelf(ModBlocks.ARENITE.get());
        dropSelf(ModBlocks.COMPACT_SAND.get());

        dropSelf(ModBlocks.MAROON_WOOL.get());
        dropSelf(ModBlocks.MAROON_CARPET.get());
        dropSelf(ModBlocks.MAROON_CONCRETE.get());
        dropSelf(ModBlocks.MAROON_CONCRETE_POWDER.get());
        dropSelf(ModBlocks.MAROON_TERRACOTTA.get());
        this.add(ModBlocks.MAROON_SHULKER_BOX.get(), this::createShulkerBoxDrop);
        dropWhenSilkTouch(ModBlocks.MAROON_STAINED_GLASS.get());
        dropWhenSilkTouch(ModBlocks.MAROON_STAINED_GLASS_PANE.get());

        dropSelf(ModBlocks.OCHRE_WOOL.get());
        dropSelf(ModBlocks.OCHRE_CARPET.get());
        dropSelf(ModBlocks.OCHRE_CONCRETE.get());
        dropSelf(ModBlocks.OCHRE_CONCRETE_POWDER.get());
        dropSelf(ModBlocks.OCHRE_TERRACOTTA.get());
        this.add(ModBlocks.OCHRE_SHULKER_BOX.get(), this::createShulkerBoxDrop);
        dropWhenSilkTouch(ModBlocks.OCHRE_STAINED_GLASS.get());
        dropWhenSilkTouch(ModBlocks.OCHRE_STAINED_GLASS_PANE.get());


        add(ModBlocks.DUNE_GRASS.get(), createShearsOnlyDrop(ModBlocks.DUNE_GRASS.get()));
        add(ModBlocks.PALM_LEAVES.get(), createLeavesDrops(ModBlocks.PALM_LEAVES.get(), ModBlocks.PALM_SAPLING.get(), 0.08F));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
