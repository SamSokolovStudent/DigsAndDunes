package net.soko.digs_and_dunes.core.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DigsAndDunes.MOD_ID);

    public static final RegistryObject<Item> SUSPICIOUS_DIRT = ITEMS.register("suspicious_dirt",
            () -> new BlockItem(ModBlocks.SUSPICIOUS_DIRT.get(), new Item.Properties()));

    public static final RegistryObject<Item> PETRIFIED_WOOD = ITEMS.register("petrified_wood",
            () -> new BlockItem(ModBlocks.PETRIFIED_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> PETRIFIED_PLANKS = ITEMS.register("petrified_wood_planks",
            () -> new BlockItem(ModBlocks.PETRIFIED_PLANKS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARENITE = ITEMS.register("arenite",
            () -> new BlockItem(ModBlocks.ARENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> COMPACT_SAND = ITEMS.register("compact_sand",
            () -> new BlockItem(ModBlocks.COMPACT_SAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> QUICKSAND_BUCKET = ITEMS.register("quicksand_bucket",
            () -> new SolidBucketItem(ModBlocks.QUICKSAND.get(), SoundEvents.BRUSH_SAND, (new Item.Properties()).stacksTo(1)));

    public static final RegistryObject<Item> PALM_LOG = ITEMS.register("palm_log",
            () -> new BlockItem(ModBlocks.PALM_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_WOOD = ITEMS.register("palm_wood",
            () -> new BlockItem(ModBlocks.PALM_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_PALM_LOG = ITEMS.register("stripped_palm_log",
            () -> new BlockItem(ModBlocks.STRIPPED_PALM_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_PALM_WOOD = ITEMS.register("stripped_palm_wood",
            () -> new BlockItem(ModBlocks.STRIPPED_PALM_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_LEAVES = ITEMS.register("palm_leaves",
            () -> new BlockItem(ModBlocks.PALM_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_HUSK = ITEMS.register("palm_husk",
            () -> new BlockItem(ModBlocks.PALM_HUSK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DATE = ITEMS.register("date",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.2F).build())));

    public static final RegistryObject<Item> PALM_PLANKS = ITEMS.register("palm_planks",
            () -> new BlockItem(ModBlocks.PALM_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_SLAB = ITEMS.register("palm_slab",
            () -> new BlockItem(ModBlocks.PALM_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_STAIRS = ITEMS.register("palm_stairs",
            () -> new BlockItem(ModBlocks.PALM_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_DOOR = ITEMS.register("palm_door",
            () -> new BlockItem(ModBlocks.PALM_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_FENCE = ITEMS.register("palm_fence",
            () -> new BlockItem(ModBlocks.PALM_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_FENCE_GATE = ITEMS.register("palm_fence_gate",
            () -> new BlockItem(ModBlocks.PALM_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_PRESSURE_PLATE = ITEMS.register("palm_pressure_plate",
            () -> new BlockItem(ModBlocks.PALM_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_BUTTON = ITEMS.register("palm_button",
            () -> new BlockItem(ModBlocks.PALM_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> PALM_TRAPDOOR = ITEMS.register("palm_trapdoor",
            () -> new BlockItem(ModBlocks.PALM_TRAPDOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> PALM_SIGN = ITEMS.register("palm_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.PALM_SIGN.get(), ModBlocks.PALM_WALL_SIGN.get()));
    public static final RegistryObject<Item> PALM_HANGING_SIGN = ITEMS.register("palm_hanging_sign",
            () -> new HangingSignItem(ModBlocks.PALM_HANGING_SIGN.get(), ModBlocks.PALM_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> PALM_SAPLING = ITEMS.register("palm_sapling",
            () -> new BlockItem(ModBlocks.PALM_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> POTTED_PALM_SAPLING = ITEMS.register("potted_palm_sapling",
            () -> new BlockItem(ModBlocks.POTTED_PALM_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> DUNE_GRASS = ITEMS.register("dune_grass",
            () -> new BlockItem(ModBlocks.DUNE_GRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> POTTERY_TABLE = ITEMS.register("dune_grass_block",
            () -> new BlockItem(ModBlocks.POTTERY_TABLE.get(), new Item.Properties()));
}
