package net.soko.digs_and_dunes.core;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.soko.digs_and_dunes.core.event.CommonEvents;
import net.soko.digs_and_dunes.core.registry.*;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DigsAndDunes.MOD_ID)
public class DigsAndDunes {
    public static final String MOD_ID = "digs_and_dunes";

    public static final Logger LOGGER = LogUtils.getLogger();

    public DigsAndDunes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModCreativeModeTab.CREATIVE_MODE_TABS.register(modEventBus);
        ModTrunkPlacerType.TRUNK_PLACERS.register(modEventBus);
        ModFoliagePlacerType.FOLIAGE_PLACERS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModCreativeModeTab::buildContents);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(CommonEvents::onItemRightClick);

        if (FMLEnvironment.dist.isClient()) {
            DigsAndDunesClient.init();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModCriteria.init();
        ModMessages.register();
    }

}
