package net.soko.digs_and_dunes.core.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.common.block.ModFlammableRotatedPillarBlock;
import net.soko.digs_and_dunes.common.block.custom.DateBlock;
import net.soko.digs_and_dunes.common.block.custom.PalmFrondBlock;
import net.soko.digs_and_dunes.common.block.custom.PotteryTableBlock;
import net.soko.digs_and_dunes.common.block.custom.Quicksand;
import net.soko.digs_and_dunes.common.worldgen.feature.generator.PalmTreeGrower;
import net.soko.digs_and_dunes.core.DigsAndDunes;
import net.soko.digs_and_dunes.util.ModDyeColors;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DigsAndDunes.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DigsAndDunes.MOD_ID);

    public static final RegistryObject<Block> SUSPICIOUS_DIRT = BLOCKS.register("suspicious_dirt",
            () -> new BrushableBlock(Blocks.DIRT.defaultBlockState().getBlock(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DIRT)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(0.5F)
                            .sound(SoundType.ROOTED_DIRT)
                            .pushReaction(PushReaction.DESTROY),
                    SoundEvents.BRUSH_GENERIC,
                    SoundEvents.BRUSH_GRAVEL_COMPLETED
            ));

    public static final RegistryObject<Block> PETRIFIED_WOOD = BLOCKS.register("petrified_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(SoundType.STEM)));
    public static final RegistryObject<Block> PETRIFIED_PLANKS = BLOCKS.register("petrified_wood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(SoundType.STEM)));

    public static final RegistryObject<Block> ARENITE = BLOCKS.register("arenite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRANITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COMPACT_SAND = BLOCKS.register("compact_sand",
            () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> QUICKSAND = BLOCKS.register("quicksand",
            () -> new Quicksand((BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.25F).sound(SoundType.SAND).dynamicShape().noOcclusion()), 15916470));

    public static final RegistryObject<Block> PALM_LOG = BLOCKS.register("palm_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> PALM_WOOD = BLOCKS.register("palm_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_PALM_LOG = BLOCKS.register("stripped_palm_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_PALM_WOOD = BLOCKS.register("stripped_palm_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final RegistryObject<Block> PALM_HUSK = BLOCKS.register("palm_husk",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MANGROVE_ROOTS).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DATE = BLOCKS.register("date",
            () -> new DateBlock(BlockBehaviour.Properties.copy(Blocks.COCOA)));

    public static final RegistryObject<Block> PALM_LEAVES = BLOCKS.register("palm_leaves",
            () -> new PalmFrondBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PALM_PLANKS = BLOCKS.register("palm_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> PALM_SLAB = BLOCKS.register("palm_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> PALM_STAIRS = BLOCKS.register("palm_stairs",
            () -> new StairBlock(() -> PALM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> PALM_DOOR = BLOCKS.register("palm_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
    public static final RegistryObject<Block> PALM_FENCE = BLOCKS.register("palm_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> PALM_FENCE_GATE = BLOCKS.register("palm_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> PALM_PRESSURE_PLATE = BLOCKS.register("palm_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> PALM_BUTTON = BLOCKS.register("palm_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> PALM_TRAPDOOR = BLOCKS.register("palm_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).ignitedByLava().noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> PALM_SIGN = BLOCKS.register("palm_sign",
            () -> new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), WoodType.OAK));
    public static final RegistryObject<Block> PALM_WALL_SIGN = BLOCKS.register("palm_wall_sign",
            () -> new WallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).lootFrom(PALM_SIGN).ignitedByLava(), WoodType.OAK));
    public static final RegistryObject<Block> PALM_HANGING_SIGN = BLOCKS.register("palm_hanging_sign",
            () -> new CeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), WoodType.OAK));
    public static final RegistryObject<Block> PALM_WALL_HANGING_SIGN = BLOCKS.register("palm_wall_hanging_sign",
            () -> new WallHangingSignBlock(BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(PALM_HANGING_SIGN), WoodType.OAK));

    public static final RegistryObject<Block> PALM_SAPLING = BLOCKS.register("palm_sapling",
            () -> new SaplingBlock(new PalmTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)) {
                @Override
                protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
                    return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(BlockTags.SAND);
                }
            });
    public static final RegistryObject<Block> POTTED_PALM_SAPLING = BLOCKS.register("potted_palm_sapling",
            () -> new FlowerPotBlock(null, PALM_SAPLING, BlockBehaviour.Properties.copy(Blocks.POTTED_OAK_SAPLING)));

    public static final RegistryObject<Block> DUNE_GRASS = BLOCKS.register("dune_grass",
            () -> new TallGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY)) {
                @Override
                protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
                    return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(BlockTags.SAND);
                }
            }
    );

    public static final RegistryObject<Block> POTTERY_TABLE = BLOCKS.register("pottery_table",
            () -> new PotteryTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static final RegistryObject<Block> MAROON_WOOL = BLOCKS.register("maroon_wool",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
    public static final RegistryObject<Block> MAROON_CARPET = BLOCKS.register("maroon_carpet",
            () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_CARPET)));
//    public static final RegistryObject<Block> MAROON_BED = BLOCKS.register("maroon_bed",
//            () -> new BedBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BED), DyeColor.RED));
//    public static final RegistryObject<Block> MAROON_BANNER = BLOCKS.register("maroon_banner",
//            () -> new BannerBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER), DyeColor.RED));
//    public static final RegistryObject<Block> MAROON_WALL_BANNER = BLOCKS.register("maroon_wall_banner",
//            () -> new WallBannerBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WALL_BANNER), DyeColor.RED));
    public static final RegistryObject<Block> MAROON_SHULKER_BOX = BLOCKS.register("maroon_shulker_box",
            () -> new ShulkerBoxBlock(ModDyeColors.digsAndDunes$MAROON, BlockBehaviour.Properties.copy(Blocks.SHULKER_BOX)));
//    public static final RegistryObject<Block> MAROON_GLAZED_TERRACOTTA = BLOCKS.register("maroon_glazed_terracotta",
//            () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_GLAZED_TERRACOTTA)));
    public static final RegistryObject<Block> MAROON_CONCRETE = BLOCKS.register("maroon_concrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_CONCRETE).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> MAROON_CONCRETE_POWDER = BLOCKS.register("maroon_concrete_powder",
            () -> new ConcretePowderBlock(MAROON_CONCRETE.get(), BlockBehaviour.Properties.of().mapColor(DyeColor.valueOf("digsAndDunes$MAROON")).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> MAROON_STAINED_GLASS = BLOCKS.register("maroon_stained_glass",
            () -> new StainedGlassBlock(ModDyeColors.digsAndDunes$MAROON, BlockBehaviour.Properties.copy(Blocks.WHITE_STAINED_GLASS)));
    public static final RegistryObject<Block> MAROON_STAINED_GLASS_PANE = BLOCKS.register("maroon_stained_glass_pane",
            () -> new StainedGlassPaneBlock(DyeColor.valueOf("digsAndDunes$MAROON"), BlockBehaviour.Properties.copy(Blocks.WHITE_STAINED_GLASS_PANE)));
    public static final RegistryObject<Block> MAROON_TERRACOTTA = BLOCKS.register("maroon_terracotta",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_TERRACOTTA)));

    public static final RegistryObject<Block> OCHRE_WOOL = BLOCKS.register("ochre_wool",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
    public static final RegistryObject<Block> OCHRE_CARPET = BLOCKS.register("ochre_carpet",
            () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_CARPET)));
//  public static final RegistryObject<Block> OCHRE_BED = BLOCKS.register("ochre_bed",
//        () -> new BedBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BED), DyeColor.ORANGE));
//  public static final RegistryObject<Block> OCHRE_BANNER = BLOCKS.register("ochre_banner",
//        () -> new BannerBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER), DyeColor.ORANGE));
//  public static final RegistryObject<Block> OCHRE_WALL_BANNER = BLOCKS.register("ochre_wall_banner",
//        () -> new WallBannerBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WALL_BANNER), DyeColor.ORANGE));
    public static final RegistryObject<Block> OCHRE_SHULKER_BOX = BLOCKS.register("ochre_shulker_box",
            () -> new ShulkerBoxBlock(DyeColor.valueOf("digsAndDunes$OCHRE"), BlockBehaviour.Properties.copy(Blocks.SHULKER_BOX)));
//     public static final RegistryObject<Block> OCHRE_GLAZED_TERRACOTTA = BLOCKS.register("ochre_glazed_terracotta",
//        () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_GLAZED_TERRACOTTA)));
    public static final RegistryObject<Block> OCHRE_CONCRETE = BLOCKS.register("ochre_concrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_CONCRETE).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryObject<Block> OCHRE_CONCRETE_POWDER = BLOCKS.register("ochre_concrete_powder",
            () -> new ConcretePowderBlock(OCHRE_CONCRETE.get(), BlockBehaviour.Properties.of().mapColor(DyeColor.valueOf("digsAndDunes$OCHRE")).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> OCHRE_STAINED_GLASS = BLOCKS.register("ochre_stained_glass",
            () -> new StainedGlassBlock(DyeColor.valueOf("digsAndDunes$OCHRE"), BlockBehaviour.Properties.copy(Blocks.WHITE_STAINED_GLASS)));
    public static final RegistryObject<Block> OCHRE_STAINED_GLASS_PANE = BLOCKS.register("ochre_stained_glass_pane",
            () -> new StainedGlassPaneBlock(DyeColor.valueOf("digsAndDunes$OCHRE"), BlockBehaviour.Properties.copy(Blocks.WHITE_STAINED_GLASS_PANE)));
    public static final RegistryObject<Block> OCHRE_TERRACOTTA = BLOCKS.register("ochre_terracotta",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_TERRACOTTA)));

}
