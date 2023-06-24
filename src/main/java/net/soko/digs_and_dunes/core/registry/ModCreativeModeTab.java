package net.soko.digs_and_dunes.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DigsAndDunes.MOD_ID);

    public static RegistryObject<CreativeModeTab> DIGS_AND_DUNES = CREATIVE_MODE_TABS.register("digs_and_dunes", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Items.SAND))
                    .title(Component.translatable("itemGroup.digs_and_dunes")).build());

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == DIGS_AND_DUNES.get()) {
            event.accept(ModItems.SUSPICIOUS_DIRT.get());

            event.accept(ModItems.PETRIFIED_WOOD.get());
            event.accept(ModItems.PETRIFIED_PLANKS.get());

            event.accept(ModItems.ARENITE.get());
            event.accept(ModItems.COMPACT_SAND.get());
            event.accept(ModItems.QUICKSAND_BUCKET.get());

            event.accept(ModItems.PALM_LOG.get());
            event.accept(ModItems.PALM_WOOD.get());
            event.accept(ModItems.STRIPPED_PALM_LOG.get());
            event.accept(ModItems.STRIPPED_PALM_WOOD.get());
            event.accept(ModItems.PALM_LEAVES.get());
            event.accept(ModItems.PALM_HUSK.get());
            event.accept(ModItems.DATE.get());

            event.accept(ModItems.PALM_PLANKS.get());
            event.accept(ModItems.PALM_SLAB.get());
            event.accept(ModItems.PALM_STAIRS.get());
            event.accept(ModItems.PALM_DOOR.get());
            event.accept(ModItems.PALM_FENCE.get());
            event.accept(ModItems.PALM_FENCE_GATE.get());
            event.accept(ModItems.PALM_PRESSURE_PLATE.get());
            event.accept(ModItems.PALM_BUTTON.get());
            event.accept(ModItems.PALM_TRAPDOOR.get());

            event.accept(ModItems.PALM_SIGN.get());
            event.accept(ModItems.PALM_HANGING_SIGN.get());

            event.accept(ModItems.PALM_SAPLING.get());
            event.accept(ModItems.POTTED_PALM_SAPLING.get());

            event.accept(ModItems.DUNE_GRASS.get());

            event.accept(ModItems.POTTERY_TABLE.get());
        }
    }

}
