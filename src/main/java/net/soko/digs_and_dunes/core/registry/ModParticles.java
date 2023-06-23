package net.soko.digs_and_dunes.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.soko.digs_and_dunes.core.DigsAndDunes;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DigsAndDunes.MOD_ID);

    public static void init() {
        PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<SimpleParticleType> createParticle(String name) {
        return PARTICLES.register(name, () -> new SimpleParticleType(false));
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
    }
}